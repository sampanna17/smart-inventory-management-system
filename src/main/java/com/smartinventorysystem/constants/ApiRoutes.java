package com.smartinventorysystem.constants;

public final class ApiRoutes {

    private ApiRoutes() {}

    public static final String API = "/api";

    public static final class Auth {
        public static final String BASE = API + "/auth";

        public static final String SIGNUP = "/signup";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String ACTIVATE = "/activate";
        public static final String RESEND_ACTIVATE = "/resend-activation";

        private Auth() {}
    }

    public static final class Users {
        public static final String BASE = API + "/users";

        public static final String UPDATE_PROFILE = "/update-profile";
        public static final String DELETE_ADMIN = "/admin/{adminId}";
        public static final String DELETE_STAFF = "/staff/{staffId}";
        public static final String CREATE_STAFF = "/create-staff";
        public static final String GET_BY_ID = "/{userId}";
        public static final String DEACTIVATE_STAFF = "/staff/{staffId}/deactivate";
        public static final String ACTIVATE_STAFF = "/staff/{staffId}/activate";

        private Users() {}
    }

    public static final class Categories {

        public static final String BASE = API + "/categories";

        public static final String BY_ID = "/{id}";
        public static final String CREATE = "/create";
        public static final String UPDATE = "/update/{id}";
        public static final String DELETE = "/{id}";
        public static final String ALL = "";

        private Categories() {}
    }

    public static final class Suppliers {
        public static final String BASE = API + "/suppliers";

        public static final String CREATE = "/create";
        public static final String UPDATE = "/update/{supplierId}";
        public static final String DELETE = "/{supplierId}";
        public static final String GET_BY_ID = "/{supplierId}";
        public static final String GET_ALL = "";

        private Suppliers() {}
    }

    public static final class Customers {
        public static final String BASE = API + "/customers";

        public static final String CREATE = "/create";
        public static final String UPDATE = "/update/{customerId}";
        public static final String DELETE = "/{customerId}";
        public static final String GET_BY_ID = "/{customerId}";
        public static final String GET_ALL = "";

        private Customers() {}
    }

    public static final class Units {
        public static final String BASE = API + "/units";

        public static final String CREATE = "/create";
        public static final String UPDATE = "/update/{unitId}";
        public static final String DELETE = "/{unitId}";
        public static final String GET_BY_ID = "/{unitId}";
        public static final String GET_ALL = "";

        private Units() {}
    }

    public static final class Products {
        public static final String BASE = API + "/products";

        public static final String CREATE = "/create";
        public static final String UPDATE = "/update/{productId}";
        public static final String DELETE = "/{productId}";
        public static final String GET_BY_ID = "/{productId}";
        public static final String GET_ALL = "";

        private Products() {}
    }

    public static final class ProductImages {
        public static final String BASE = API + "/products/{productId}/images";

        public static final String UPLOAD = "";
        public static final String GET_ALL = "";
        public static final String DELETE = "/{imageId}";

        private ProductImages() {}
    }

    public static final class ProductSuppliers {

        public static final String BASE = API + "/product-suppliers";

        public static final String ADD = "/products/{productId}/suppliers/{supplierId}";
        public static final String REMOVE = "/products/{productId}/suppliers/{supplierId}";
        public static final String GET_SUPPLIERS_BY_PRODUCT = "/products/{productId}/suppliers";
        public static final String GET_PRODUCTS_BY_SUPPLIER = "/suppliers/{supplierId}/products";

        private ProductSuppliers(){}
    }

    public static final class Purchases {
        public static final String BASE = API + "/purchases";

        public static final String CREATE = "/create";
        public static final String UPDATE = "/update/{purchaseId}";
        public static final String DELETE = "/{purchaseId}";
        public static final String GET_BY_ID = "/{purchaseId}";
        public static final String GET_ALL = "";
        public static final String UPDATE_STATUS = "/{purchaseId}/status";
        public static final String GET_BY_SUPPLIER = "/supplier/{supplierId}";

        private Purchases() {}
    }

}