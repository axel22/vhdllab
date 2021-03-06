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
package hr.fer.zemris.vhdllab.dao.impl;

import hr.fer.zemris.vhdllab.dao.PredefinedFileDao;
import hr.fer.zemris.vhdllab.entity.File;
import hr.fer.zemris.vhdllab.entity.FileType;
import hr.fer.zemris.vhdllab.service.util.WebUtils;
import hr.fer.zemris.vhdllab.util.IOUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

public class PredefinedFileDaoImpl implements PredefinedFileDao,
        ServletContextAware {

    /**
     * Logger for this class
     */
    private static final Logger LOG = Logger
            .getLogger(PredefinedFileDaoImpl.class);

    private static final String DEFAULT_LOCATION = "/WEB-INF/predefined";
    private static final String README_FILE_NAME = "README.txt";
    private java.io.File location;
    private ServletContext servletContext;

    private Map<String, File> files = new HashMap<String, File>();

    public void setLocation(java.io.File location) {
        this.location = location;
    }

    public java.io.File getLocation() {
        if (location == null) {
            location = WebUtils.getLocation(servletContext, DEFAULT_LOCATION);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Using predefined files location: "
                    + location.getAbsolutePath());
        }
        return location;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostConstruct
    public void initFiles() {
        for (java.io.File predefined : getLocation().listFiles()) {
            if (predefined.isFile() && !isReadme(predefined)) {
                String contents;
                try {
                    contents = FileUtils.readFileToString(predefined,
                            IOUtil.DEFAULT_ENCODING);
                } catch (IOException e) {
                    throw new UnhandledException(e);
                }
                String name = predefined.getName();
                File file = new File(name, FileType.PREDEFINED, contents);
                files.put(name, file);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Loaded predefined file: " + name);
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Initialized with " + files.size()
                            + " predefined files.");
        }
    }

    private boolean isReadme(java.io.File file) {
        return file.getName().equals(README_FILE_NAME);
    }

    @Override
    public Set<File> getPredefinedFiles() {
        return createPredefinedFilesSet();
    }

    private Set<File> createPredefinedFilesSet() {
        Collection<File> values = files.values();
        Set<File> list = new HashSet<File>(values.size());
        for (File f : values) {
            list.add(new File(f)); // to reduce aliasing problems
        }
        return list;
    }

    @Override
    public File findByName(String name) {
        Validate.notNull(name, "Name can't be null");
        File file = files.get(name);
        if (file != null) {
            file = new File(file); // to reduce aliasing problems
        }
        return file;
    }

}
