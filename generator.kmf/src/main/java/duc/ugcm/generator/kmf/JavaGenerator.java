package duc.ugcm.generator.kmf;

import duc.ugcm.ast.Class;
import duc.ugcm.ast.Model;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Properties;

public class JavaGenerator {
    private static void cleanOutput(File rootOut) throws IOException{
        Files.walkFileTree(rootOut.toPath(), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });

        Files.deleteIfExists(rootOut.toPath());
        Files.createDirectories(rootOut.toPath());
    }




    public static void generate(Model model, File outPath, String mmName) throws IOException {
        cleanOutput(outPath);
        Properties properties = new Properties();
        properties.put("file.resource.loader.class",ClasspathResourceLoader.class.getName());
        Velocity.init(properties);

        List<Class> modelClasses = model.getClasses();

        for (int i = 0; i < modelClasses.size(); i++) {
            Class c = modelClasses.get(i);

            String cPath = c.getFqn().replace('.',File.separatorChar) + ".java";
            File apiFile = new File(outPath.getAbsolutePath() + File.separator + cPath);
            velocify(c, mmName, apiFile, "ClassTemplate.vm");

            String implPath = c.getPackName().replace('.', File.separatorChar) + File.separator + "impl" + File.separator + c.getName() + "Impl.java";
            File implFile = new File(outPath.getAbsolutePath() + File.separator + implPath);
            velocify(c,mmName,implFile,"ClassImplTemplate.vm");

            String metaPath = c.getPackName().replace('.', File.separatorChar) + File.separator + "meta" + File.separator + "Meta" + c.getName() + ".java";
            File metaFile = new File(outPath.getAbsolutePath() + File.separator + metaPath);
            velocify(c,mmName,metaFile,"MetaClassTemplate.vm");
        }
    }

    private static void velocify(Class ducClass, String mmName, File localFile, String templateName) throws IOException {
        Template template = Velocity.getTemplate(templateName);
        VelocityContext vCtx = new VelocityContext();
        vCtx.put("class", ducClass);
        vCtx.put("primHelper",new PrimitiveTypeHelper());
        vCtx.put("helper", new JavaGeneratorHelper());
        String[] mmInfo = new JavaGeneratorHelper().metamodelInfo(mmName);
        vCtx.put("mmPackName", mmInfo[0]);
        vCtx.put("mmName", mmInfo[1]);

        Files.createDirectories(localFile.getParentFile().toPath());
        PrintWriter pw = new PrintWriter(localFile);
        template.merge(vCtx, pw);
        pw.flush();
        pw.close();
    }
}
