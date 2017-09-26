package com.example.rafae.booktracker.daggerExample.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by janisharali on 25/12/16.
 */

/**
 * Thus @Qualifier is used to distinguish between objects of the same type but with different
 * instances. In the above code, we have ActivityContext and ApplicationContext so that the Context object being injected can refer to the respectiveContext type.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
