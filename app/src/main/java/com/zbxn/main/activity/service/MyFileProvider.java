package com.zbxn.main.activity.service;

import java.security.Provider;

/**
 * Created by U on 2017/3/24.
 */

public class MyFileProvider extends Provider {

    /**
     * Constructs a new instance of {@code Provider} with its name, version and
     * description.
     *
     * @param name    the name of the provider.
     * @param version the version of the provider.
     * @param info
     */
    protected MyFileProvider(String name, double version, String info) {
        super(name, version, info);
    }
}
