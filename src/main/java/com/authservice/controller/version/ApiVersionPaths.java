package com.authservice.controller.version;

/**
 * Clase que centraliza las rutas base y versiones para la API REST.
 * Esto ayuda a mantener consistencia y facilidad de mantenimiento.
 */
public class ApiVersionPaths {

    /***********************************************
              Versiones generales de la API
     ***********************************************/
    public static final String API_V1 = "/api/v1";

    
    /***********************************************
	    Endpoints generales, relativos a la versión
	 ***********************************************/
    public static final String MODULE = "/module";
    public static final String PERMISSION = "/permission";

    
    /***********************************************
	  Construcción de rutas completas (version + endpoint)
	 ***********************************************/
    public static final String V1_MODULE = API_V1 + MODULE;
    public static final String V1_PERMISSION = API_V1 + PERMISSION;
    
    private ApiVersionPaths() {}

}
