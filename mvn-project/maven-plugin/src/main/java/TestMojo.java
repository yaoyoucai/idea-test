import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "sayhi")
public class TestMojo extends AbstractMojo {
    @Parameter(property = "greeting",defaultValue = "aaaaa")
    private String greeting;
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(greeting);
        
    }
}
