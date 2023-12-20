package com.guodong.android.sword.kcp.kotlin

import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

/**
 * Created by guodongAndroid on 2023/11/21.
 */
@RunWith(Suite::class)
@SuiteClasses(
    TestAnonymousClass::class,
    TestCollectionArg::class,
    TestInnerClass::class,
    TestLocalFunction::class,
    TestNoArg::class,
    TestPrimitiveArg::class,
    TestPrimitiveArrayArg::class,
    TestProxyDisabled::class,
    TestReferenceArg::class,
    TestTopLevelAndObject::class,
    TestDeclarationChecker::class,
)
class KcpTestRunner {
}