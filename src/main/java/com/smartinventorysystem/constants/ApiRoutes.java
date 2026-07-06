package com.smartinventorysystem.constants;

public final class ApiRoutes {

    private ApiRoutes() {}

    public static final class Auth {
        public static final String BASE = "/api/auth";
        public static final String SIGNUP = "/signup";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String ACTIVATE = "/activate";

        private Auth() {}
    }

    public static final class Users {
        public static final String BASE = "/api/users";
        public static final String UPDATE_PROFILE = "/update-profile";
        public static final String DELETE_ADMIN = "/admin/{adminId}";
        public static final String DELETE_STAFF = "/staff/{staffId}";
        public static final String CREATE_STAFF = "/create-staff";
        public static final String GET_BY_ID = "/{userId}";
        public static final String DEACTIVATE_STAFF = "/staff/{staffId}/deactivate";

        private Users() {}
    }

    public static final class Products {
        public static final String BASE = "/api/products";

        private Products() {}
    }

    public static final class ProductImages {
        public static final String BASE = "/api/products/images";

        private ProductImages() {}
    }

    public static final class Categories {
        public static final String BASE = "/api/categories";

        private Categories() {}
    }

    public static final class Suppliers {
        public static final String BASE = "/api/suppliers";

        private Suppliers() {}
    }

    public static final class Inventory {
        public static final String BASE = "/api/inventory";

        private Inventory() {}
    }

    public static final class Customers {
        public static final String BASE = "/api/customers";

        private Customers() {}
    }
}