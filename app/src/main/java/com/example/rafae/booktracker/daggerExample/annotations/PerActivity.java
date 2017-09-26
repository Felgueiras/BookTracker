package com.example.rafae.booktracker.daggerExample.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by janisharali on 08/12/16.
 */

/**
 * @Scope is used to specify the scope in which a dependency object persists. If a class getting dependencies, have members injected with classes annotated with a scope, then each instance of that class asking for dependencies will get its own set of member variables.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}

