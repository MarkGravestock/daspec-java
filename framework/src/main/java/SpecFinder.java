import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.io.FileUtils.listFiles;

public class SpecFinder {
    Logger logger = LoggerFactory.getLogger(SpecFinder.class);

    private Class aClass;

    public SpecFinder(Class aClass) {
        this.aClass = aClass;
    }

    public List<Spec> getSpecs() {
        File resourcesDirectory = new File("build/resources/test");
        logger.info("Using location: " + resourcesDirectory.getAbsolutePath());

        Collection<File> files = listFiles(resourcesDirectory, new String[]{"md"}, true);
        List<Spec> fileContents = new ArrayList<>();
        files.forEach(x -> {
            try {
                Spec spec = new Spec();
                spec.markDown = FileUtils.readFileToString(x, Charset.defaultCharset().toString());
                spec.source = x.getName();
                fileContents.add(spec);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return fileContents;
    }
}
