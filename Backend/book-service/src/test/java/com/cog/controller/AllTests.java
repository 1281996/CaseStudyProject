package com.cog.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AuthorControllerTest.class,BookControllerTest.class,ReaderControllerTest.class})
public class AllTests {

}
