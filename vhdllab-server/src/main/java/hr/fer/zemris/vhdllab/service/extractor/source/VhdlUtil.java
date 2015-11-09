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
package hr.fer.zemris.vhdllab.service.extractor.source;

/**
 * Helper class for manipulating VHDL source code.
 */
public final class VhdlUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private VhdlUtil() {
    }

    /**
     * Removes all comments from VHDL source. Comments begins with two
     * successive minus signs (--) and go to the end of the line.
     *
     * @param source
     *            VHDL source code that can include comments
     * @return VHDL source code without comments
     */
    public static String decomment(String source) {
        char[] chs = source.toCharArray();
        int pos = 0;
        int i;
        for (i = 0; i < chs.length - 1; i++) {
            chs[pos] = chs[i];
            if (chs[i] == '-' && chs[i + 1] == '-') {
                i += 2;
                while (i < chs.length && chs[i] != '\n') {
                    i++;
                }
                continue;
            }
            pos++;
        }
        if (i < chs.length) {
            chs[pos] = chs[i];
            pos++;
        }
        if (pos == 0) {
            return "";
        }
        return String.valueOf(chs, 0, pos);
    }

    /**
     * Replace any succession of whitespaces with a single whitespace sign.
     * Whitespaces include tabs, spaces, CR and LF.
     *
     * @param source
     *            VHDL source code with arbitrary successions of whitespace
     *            characters.
     * @return VHDL source where only whitespace character is the space
     *         character, and there are no whitespace successions.
     */
    public static String removeWhiteSpaces(String source) {
        String s = source;
        s = s.replaceAll("\t+", " ");
        s = s.replaceAll("\r+", " ");
        s = s.replaceAll("\n+", " ");
        s = s.replaceAll(" +", " ");
        return s.trim();
    }

}
