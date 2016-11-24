package com.clouway.core;

import com.clouway.http.servlets.HtmlServletPageRenderer;
import com.clouway.persistent.datastore.DataStore;
import com.google.inject.AbstractModule;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class CoreModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ServletPageRenderer.class).to(HtmlServletPageRenderer.class);
    bind(String.class).toProvider(UuidGenerator.class);
    bind(DataStore.class);
    bind(Validator.class).to(RegexValidator.class);
    bind(MyClock.class).to(MyServerClock.class);
    bind(Template.class).to(HtmlTemplate.class);
  }

}
