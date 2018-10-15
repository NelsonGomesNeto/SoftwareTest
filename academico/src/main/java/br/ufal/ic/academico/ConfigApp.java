package br.ufal.ic.academico;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Willy
 */
@Getter
@Setter
public class ConfigApp extends Configuration {
    
    private String university;
    private String state;
    private int port;
    
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
}