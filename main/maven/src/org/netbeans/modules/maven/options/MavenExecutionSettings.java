/* ==========================================================================
 * Copyright 2006 Mevenide Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * =========================================================================
 */

package org.netbeans.modules.maven.options;

import hidden.org.codehaus.plexus.util.cli.Arg;
import hidden.org.codehaus.plexus.util.cli.CommandLineException;
import hidden.org.codehaus.plexus.util.cli.CommandLineUtils;
import hidden.org.codehaus.plexus.util.cli.Commandline;
import hidden.org.codehaus.plexus.util.cli.StreamConsumer;
import java.io.File;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.maven.execution.MavenExecutionRequest;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbPreferences;
import org.openide.util.Utilities;

/**
 * a netbeans settings for global options that cannot be put into the settings file.
 * @author mkleint
 */
public class MavenExecutionSettings  {
    public static final String PROP_DEBUG = "showDebug"; // NOI18N
    public static final String PROP_ERRORS = "showErrors"; //NOI18N
    public static final String PROP_CHECKSUM_POLICY = "checksumPolicy"; //NOI18N
    public static final String PROP_PLUGIN_POLICY = "pluginUpdatePolicy"; //NOI18N
    public static final String PROP_FAILURE_BEHAVIOUR = "failureBehaviour"; //NOI18N
    public static final String PROP_USE_REGISTRY = "usePluginRegistry"; //NOI18N
    public static final String PROP_SYNCH_PROXY = "synchronizeProxySettings"; //NOI18N
    public static final String PROP_USE_COMMANDLINE = "useCommandLineMaven"; //NOI18N
    public static final String PROP_COMMANDLINE_PATH = "commandLineMavenPath"; //NOI18N
    public static final String PROP_SHOW_RUN_DIALOG = "showRunDialog"; //NOI18N
    
    private static final MavenExecutionSettings INSTANCE = new MavenExecutionSettings();
    
    public static MavenExecutionSettings getDefault() {
        return INSTANCE;
    }
    
    protected final Preferences getPreferences() {
        return NbPreferences.forModule(MavenExecutionSettings.class);
    }
    
    protected final String putProperty(String key, String value) {
        String retval = getProperty(key);
        if (value != null) {
            getPreferences().put(key, value);
        } else {
            getPreferences().remove(key);
        }
        return retval;
    }

    protected final String getProperty(String key) {
        return getPreferences().get(key, null);
    }    
    
    private MavenExecutionSettings() {
    }
    

    public boolean isShowDebug() {
        return getPreferences().getBoolean(PROP_DEBUG, false);
    }

    public void setShowDebug(boolean showDebug) {
        getPreferences().putBoolean(PROP_DEBUG, showDebug);
    }

    public boolean isShowErrors() {
        return getPreferences().getBoolean(PROP_ERRORS, false);
    }

    public void setShowErrors(boolean showErrors) {
        getPreferences().putBoolean(PROP_ERRORS, showErrors);
    }

    public String getChecksumPolicy() {
        return getPreferences().get(PROP_CHECKSUM_POLICY, null);
    }

    public void setChecksumPolicy(String checksumPolicy) {
        putProperty(PROP_CHECKSUM_POLICY, checksumPolicy);
    }

    public Boolean getPluginUpdatePolicy() {
        String prop = getProperty(PROP_PLUGIN_POLICY);
        return prop == null ? null : Boolean.parseBoolean(prop);
    }

    public void setPluginUpdatePolicy(Boolean pluginUpdatePolicy) {
        if (pluginUpdatePolicy == null) {
            getPreferences().remove(PROP_PLUGIN_POLICY);
        } else {
            putProperty(PROP_PLUGIN_POLICY, pluginUpdatePolicy.toString());
        }
    }

    public String getFailureBehaviour() {
        return getPreferences().get(PROP_FAILURE_BEHAVIOUR, MavenExecutionRequest.REACTOR_FAIL_FAST);
    }

    public void setFailureBehaviour(String failureBehaviour) {
        putProperty(PROP_FAILURE_BEHAVIOUR, failureBehaviour);
    }

    public boolean isUsePluginRegistry() {
        return getPreferences().getBoolean(PROP_USE_REGISTRY, true);
    }

    public void setUsePluginRegistry(boolean usePluginRegistry) {
        getPreferences().putBoolean(PROP_USE_REGISTRY, usePluginRegistry);
    }
    
    public void setSynchronizeProxy(boolean sync) {
        getPreferences().putBoolean(PROP_SYNCH_PROXY, sync);
    }
    
    public boolean isSynchronizeProxy() {
        return getPreferences().getBoolean(PROP_SYNCH_PROXY, true);
    }
    
    public boolean isUseCommandLine() {
        return getPreferences().getBoolean(PROP_USE_COMMANDLINE, true);
    }

    public void setUseCommandLine(boolean useCommandLine) {
        getPreferences().putBoolean(PROP_USE_COMMANDLINE, useCommandLine);
    }
    
    public File getCommandLinePath() {
        String str =  getPreferences().get(PROP_COMMANDLINE_PATH, null);
        if (str != null) {
            return FileUtil.normalizeFile(new File(str));
        }
        return null;
    }

    public void setCommandLinePath(File path) {
        if (path == null) {
            getPreferences().remove(PROP_COMMANDLINE_PATH);
        } else {
            putProperty(PROP_COMMANDLINE_PATH, FileUtil.normalizeFile(path).getAbsolutePath());
        }
    }
    
    public boolean isShowRunDialog(){
     return getPreferences().getBoolean(PROP_SHOW_RUN_DIALOG, false);
    }
    public void setShowRunDialog(boolean  b){
      getPreferences().putBoolean(PROP_SHOW_RUN_DIALOG, b);
    }
    
    private static Boolean cachedMaven = null;
    
    public static boolean canFindExternalMaven() {
        File home = MavenExecutionSettings.getDefault().getCommandLinePath();
        String ex = Utilities.isWindows() ? "mvn.bat" : "mvn"; //NOI18N
        if (home != null && home.exists()) {
            File bin = new File(home, "bin" + File.separator + ex);//NOI18N
            if (bin.exists()) {
                return true;
            }
        }
        if (cachedMaven != null) {
            return cachedMaven.booleanValue();
        }
        Commandline cmdline = new Commandline();
        cmdline.setExecutable(ex);
        Arg arg = cmdline.createArg();
        arg.setValue("--version"); //NOI18N
        cmdline.addArg(arg);
        RegExpConsumer cons = new RegExpConsumer();
        try {
            int ret = CommandLineUtils.executeCommandLine(cmdline, cons, cons);
            cachedMaven = cons.hasMavenAround;
            return cons.hasMavenAround;
        } catch (CommandLineException ex1) {
            Exceptions.printStackTrace(ex1);
            cachedMaven = false;
            return false;
        }
    }
    
    static String getDefaultMavenInstanceVersion() {
        String ex = Utilities.isWindows() ? "mvn.bat" : "mvn"; //NOI18N
        Commandline cmdline = new Commandline();
        cmdline.setExecutable(ex);
        Arg arg = cmdline.createArg();
        arg.setValue("--version"); //NOI18N
        cmdline.addArg(arg);
        RegExpConsumer cons = new RegExpConsumer();
        try {
            int ret = CommandLineUtils.executeCommandLine(cmdline, cons, cons);
            return cons.version;
        } catch (CommandLineException ex1) {
            Exceptions.printStackTrace(ex1);
            return null;
        }
        
    }
    
    private static class RegExpConsumer implements StreamConsumer {

        private static final Pattern PATTERN = Pattern.compile("^Maven version:(.*)");
        boolean hasMavenAround = false;
        String version = null;

        public void consumeLine(String line) {
            Matcher match = PATTERN.matcher(line);
            if (match.matches()) {
                hasMavenAround = true;
                version = match.group(1);
            }
        }
    };
    
}
