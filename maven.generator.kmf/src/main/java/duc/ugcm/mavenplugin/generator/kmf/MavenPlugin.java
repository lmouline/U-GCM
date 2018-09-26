package duc.ugcm.mavenplugin.generator.kmf;

import duc.ugcm.generator.kmf.JavaGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class MavenPlugin extends AbstractMojo {

    /**
     * File or directory containing the mode definition.
     * Supported extension: {@link duc.ugcm.generator.kmf.JavaGenerator#FILE_EXTENSION}
     */
    @Parameter(required = true)
    private File input;

    /**
     * Root directory for the generated Java code.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/ugcm")
    private File outdir;

    /**
     * Full qualified name (Java definition) of the MetaModel
     */
    @Parameter(defaultValue = "KMFModel")
    private String metaModelQualifiedName;


    /**
     * Link to the running maven project.
     */
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            JavaGenerator.generate(input, outdir, metaModelQualifiedName);
        }catch (Exception e) {
            getLog().error(e);
            throw new MojoExecutionException("U-GCM Compilation error.", e);
        }

        project.addCompileSourceRoot(outdir.getAbsolutePath());
    }
}
