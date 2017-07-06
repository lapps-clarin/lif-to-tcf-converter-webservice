package de.tuebingen.uni.sfs.lapps.lifconverter;

import de.thomaskrille.dropwizard_template_config.TemplateConfigBundle;
import de.tuebingen.uni.sfs.lapps.lifconverter.resources.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ConverterApplication extends Application<ConverterConfiguration> {
    public static void main(String[] args) throws Exception{
        new ConverterApplication().run(args);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void initialize(Bootstrap<ConverterConfiguration> bootstrap) {
        bootstrap.addBundle(new TemplateConfigBundle());
    }

    @Override
    public void run(ConverterConfiguration configuration, Environment environment) throws Exception {
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
