package com.dpforge.hevery.cli;

import com.dpforge.hevery.cli.handler.HookHandler;

import java.util.HashMap;
import java.util.Map;

class HookRegistry {

    private final Map<String, Class<?>> hookNameMap = new HashMap<>();
    private final Map<String, Class<? extends HookHandler<?>>> hookHandlerMap = new HashMap<>();

    <HOOK> void registerHook(String hookName, Class<HOOK> hookClass, Class<? extends HookHandler<HOOK>> handlerClass) {
        hookNameMap.put(hookName, hookClass);
        hookHandlerMap.put(hookName, handlerClass);
    }

    Class<?> getHookClass(String hookName) {
        final Class<?> hookClass = hookNameMap.get(hookName);
        if (hookClass == null) {
            throw new IllegalStateException("No hook class is registered for '" + hookName + "'");
        }
        return hookClass;
    }

    HookHandler<?> getHandler(String hookName) throws ReflectiveOperationException {
        final Class<? extends HookHandler<?>> handler = hookHandlerMap.get(hookName);
        if (handler == null) {
            throw new IllegalStateException("No hook handler is register for '" + hookName + "'");
        }
        return handler.newInstance();
    }
}
