package com.example.rafae.booktracker.daggerExample.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by janisharali on 08/12/16.
 */

/**
 * DatabaseInfo is used to provide the database name in the class dependency.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseInfo {
}
