package duc.ugcm.generator.kmf;

import duc.ugcm.ast.Class;
import duc.ugcm.ast.Model;
import duc.ugcm.parser.Parser;
import org.antlr.v4.runtime.CharStreams;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Properties;

public class JavaGenerator {
    public static final String FILE_EXTENSION = ".ugcm";

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


    public static void generate(File input, File outDir, String mmName) throws IOException {
        String extension = input.getName();
        extension = extension.substring(extension.lastIndexOf('.'));
        if(!input.getName().endsWith(FILE_EXTENSION)) {
            throw new RuntimeException("File extension is not supporter by the Java code generator. Extension accepted: " + FILE_EXTENSION + ". Here: " + extension);
        }

        FileInputStream is = new FileInputStream(input);
        Model model = new Parser().parse(CharStreams.fromStream(is));
        generate(model, outDir, mmName);
    }


    public static void generate(Model model, File outPath, String mmName) throws IOException {
        cleanOutput(outPath);
        Properties properties = new Properties();
        properties.put("file.resource.loader.class",ClasspathResourceLoader.class.getName());
        Velocity.init(properties);

        String[] mmInfo = new JavaGeneratorHelper().metamodelInfo(mmName);
        String mmPackage = mmInfo[0];
        String mmEndName = mmInfo[1];

        VelocityContext vCtx = new VelocityContext();
        vCtx.put("mmPackName", mmPackage);
        vCtx.put("mmName", mmEndName);

        // Generate utilities
        String universePath = mmPackage.replace('.', File.separatorChar) + File.separator + mmName + "Universe.java";
        File universeFile = new File(outPath.getAbsolutePath() + File.separator + universePath);
        velocify(vCtx, universeFile, "UniverseTemplate.vm");

        vCtx.put("model", model);
        String modelPath = mmPackage.replace('.', File.separatorChar) + File.separator + mmName + "Model.java";
        File modelFile = new File(outPath.getAbsolutePath() + File.separator + modelPath);
        velocify(vCtx, modelFile, "ModelTemplate.vm");

        String viewPath = mmPackage.replace('.', File.separatorChar) + File.separator + mmName + "View.java";
        File viewFile = new File(outPath.getAbsolutePath() + File.separator + viewPath);
        velocify(vCtx, viewFile, "ViewTemplate.vm");

        String viewImplPath = mmPackage.replace('.', File.separatorChar) + File.separator + "impl" + File.separator + mmName + "ViewImpl.java";
        File viewImplFile = new File(outPath.getAbsolutePath() + File.separator + viewImplPath);
        velocify(vCtx, viewImplFile, "ViewImplTemplate.vm");


        vCtx.remove("model");



        // Generate files for classes
        vCtx.put("primHelper",new TypeHelper());
        vCtx.put("helper", new JavaGeneratorHelper());

        List<Class> modelClasses = model.getClasses();
        for (int i = 0; i < modelClasses.size(); i++) {
            Class c = modelClasses.get(i);
            vCtx.put("class", c);

            String cPath = c.getFqn().replace('.',File.separatorChar) + ".java";
            File apiFile = new File(outPath.getAbsolutePath() + File.separator + cPath);
            velocify(vCtx, apiFile, "ClassTemplate.vm");

            String implPath = c.getPackName().replace('.', File.separatorChar) + File.separator + "impl" + File.separator + c.getName() + "Impl.java";
            File implFile = new File(outPath.getAbsolutePath() + File.separator + implPath);
            velocify(vCtx,implFile,"ClassImplTemplate.vm");

            String metaPath = c.getPackName().replace('.', File.separatorChar) + File.separator + "meta" + File.separator + "Meta" + c.getName() + ".java";
            File metaFile = new File(outPath.getAbsolutePath() + File.separator + metaPath);
            velocify(vCtx,metaFile,"MetaClassTemplate.vm");
        }
    }

    private static void velocify(VelocityContext context, File localFile, String templateName) throws IOException {
        Template template = Velocity.getTemplate(templateName);
        Files.createDirectories(localFile.getParentFile().toPath());
        PrintWriter pw = new PrintWriter(localFile);
        template.merge(context, pw);
        pw.flush();
        pw.close();
    }
}
