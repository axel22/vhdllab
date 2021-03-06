/*******************************************************************************
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hr.fer.zemris.vhdllab.platform.remoting;

import hr.fer.zemris.vhdllab.platform.context.ApplicationContextHolder;
import hr.fer.zemris.vhdllab.platform.context.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.remoting.httpinvoker.CommonsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerClientConfiguration;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.command.CommandManager;

public class HttpClientRequestExecutor extends
        CommonsHttpInvokerRequestExecutor {

    private boolean showRetryMessage = false;

    public HttpClientRequestExecutor(HttpClient httpClient) throws IOException {
        super(httpClient);
        getHttpClient().getParams().setAuthenticationPreemptive(true);
        Properties properties = PropertiesLoaderUtils
                .loadAllProperties("server.properties");
        int port = Integer.parseInt(properties.getProperty("port"));
        Protocol easyhttps = new Protocol("https",
                new EasySSLProtocolSocketFactory(), port);
        Protocol.registerProtocol("https", easyhttps);
    }

    @SuppressWarnings("null")
    @Override
    protected void executePostMethod(HttpInvokerClientConfiguration config,
            HttpClient httpClient, PostMethod postMethod) throws IOException {
        AuthScope scope = new AuthScope(config.getCodebaseUrl(),
                AuthScope.ANY_PORT);
        super.executePostMethod(config, httpClient, postMethod);
        switch (postMethod.getStatusCode()) {
        case HttpStatus.SC_UNAUTHORIZED:
            UsernamePasswordCredentials credentials;
            if (Environment.isDevelopment() && !showRetryMessage) {
                credentials = new UsernamePasswordCredentials("test", "test");
//              credentials = new UsernamePasswordCredentials("admin", "admin");
                showRetryMessage = true;
            } else {
                CommandManager manager = Application.instance()
                        .getActiveWindow().getCommandManager();
                LoginCommand command = (LoginCommand) manager
                        .getCommand("loginCommand");
                command.execute();
                credentials = command.getCredentials();
            }
            if (credentials == null) {
                System.exit(1);
            }
            ApplicationContextHolder.getContext().setUserId(
                    credentials.getUserName());
            showRetryMessage = true;
            getHttpClient().getState().setCredentials(scope, credentials);
            executePostMethod(config, httpClient, postMethod);
            break;
        }
    }

    @Override
    protected InputStream decorateInputStream(InputStream is) throws IOException {
        return new GZIPInputStream(is);
    }

    @Override
    protected OutputStream decorateOutputStream(OutputStream os) throws IOException {
        return new GZIPOutputStream(os);
    }

}
