package com.jarvvski.networkingtest.handlers;

import com.jarvvski.networking.handlers.Handler;
import com.jarvvski.networking.handlers.MasterHandler;

import com.jarvvski.networking.messages.Data;
import org.junit.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestMasterHandler {

    MasterHandler masterHandler = null;
    Handler someHandler = null;
    Handler anotherHandler = null;

    @Test public void shouldNotBeNull() {
        masterHandler = new MasterHandler();
        assertNotNull("must not be null after init", masterHandler);
    }

    @Test public void canRegisterNewHandler() {
        masterHandler = new MasterHandler();
        someHandler   = new AbstractHandler();
        int beforeRegisterSize = masterHandler.getHandlerRegister().size();

        masterHandler.registerNewHandler(someHandler);
        int newRegisterSize = masterHandler.getHandlerRegister().size();

        assertEquals(newRegisterSize, (beforeRegisterSize + 1));
    }

    @Test public void canRegisterMultipleNewHandler() {
        masterHandler  = new MasterHandler();
        someHandler    = new AbstractHandler();
        anotherHandler = new AnotherAbstractHandler();

        Collection<Handler> handlerCollection = new LinkedList<>();
        handlerCollection.add(someHandler);
        handlerCollection.add(anotherHandler);

        int beforeRegisterSize = masterHandler.getHandlerRegister().size();

        masterHandler.registerMultipleHandlers(handlerCollection);

        int newRegisterSize = masterHandler.getHandlerRegister().size();
        assertEquals((beforeRegisterSize + handlerCollection.size()), newRegisterSize);
    }

    // @Test public void willcallCorrectHandlerForDataType() {
    //     masterHandler = new MasterHandler();
    //     someHandler   = Mockito.mock(AbstractHandler.class);
    //     masterHandler.registerNewHandler(someHandler);

    //     Data someData = new AbstractData();
    //     Mockito.verify(someHandler, Mockito.times(1)).handle(someData);
    //     masterHandler.handle(someData);
    // }
}
