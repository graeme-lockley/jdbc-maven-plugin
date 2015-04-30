package za.co.no9.jdbcdry.port.jsqldslmojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import za.co.no9.jdbcdry.model.GenerationException;
import za.co.no9.jdbcdry.model.ToolHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;

@Mojo(name = "jsqldsl")
public class JSQLDSLMojo extends AbstractMojo {
    @Parameter(defaultValue = "${basedir}/jsqldsl.xml", alias = "file", required = true)
    private File configurationFile;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().debug("JSQLDSLMojo: configurationFile: " + configurationFile.getAbsolutePath());

        try {
            processConfiguration(configurationFile);
        } catch (ConfigurationException | SQLException | GenerationException ex) {
            throw new MojoExecutionException(ex.getMessage(), ex);
        }
    }

    protected void processConfiguration(File configurationFile) throws ConfigurationException, GenerationException, SQLException {
        processConfiguration(Configuration.from(configurationFile));
    }

    protected void processConfiguration(Configuration configuration) throws ConfigurationException, GenerationException, SQLException {
        try (Connection connection = configuration.getJDBCConnection()) {
            TableFilter tableFilter = configuration.getTableFilter();

            Iterator<Target> targets = configuration.getTargets();
            while (targets.hasNext()) {
                ToolHandler toolHandler = targets.next().getToolHandler(getLog());
                toolHandler.process(connection, tableFilter);
            }
        }
    }
}
