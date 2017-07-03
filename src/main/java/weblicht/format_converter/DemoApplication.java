package weblicht.format_converter;

import weblicht.format_converter.resources.IndexResource;
import weblicht.format_converter.resources.NamedEntitiesResource;
import weblicht.format_converter.resources.ReferencesResource;
import weblicht.format_converter.resources.TokSentencesResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import weblicht.format_converter.resources.FormatConverterResource;

public class DemoApplication extends Application<DemoConfiguration> {
    public static void main(String[] args) throws Exception{
        new DemoApplication().run(args);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void initialize(Bootstrap<DemoConfiguration> bootstrap) {
    }

    @Override
    public void run(DemoConfiguration configuration, Environment environment) throws Exception {
        FormatConverterResource formatConverterResource=new FormatConverterResource();
        NamedEntitiesResource namedEntitiesResource = new NamedEntitiesResource();
        ReferencesResource referencesResource = new ReferencesResource();
        IndexResource indexResource = new IndexResource();
        environment.jersey().register(formatConverterResource);
        environment.jersey().register(namedEntitiesResource);
        environment.jersey().register(referencesResource);
        environment.jersey().register(TokSentencesResource.class);
        environment.jersey().register(indexResource);
    }
}
