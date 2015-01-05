package com.jaspersoft.jasperserver.jaxrs.client.core;

import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.testng.Assert.assertSame;

/**
 * Unit tests for {@link RequestExecution}
 */
public class RequestExecutionTest {
    
    @Test
    public void should_return_not_null_future_after_it_was_setted() {

        /* Given */
        FutureTask<Void> future = new FutureTask<Void>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return null;
            }
        });
        
        RequestExecution execution = Mockito.spy(new RequestExecution(new Runnable() {
            @Override
            public void run() {
                // NOP
            }
        }));
        Whitebox.setInternalState(execution, "future", future);

        /* When */
        Future retrieved = execution.getFuture();

        /* Then */
        assertSame(retrieved, future);
    }


    @Test
    public void should_return_not_null_task_after_setup_instance() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // NOP
            }
        };
        RequestExecution execution = new RequestExecution(r);
        assertSame(execution.getTask(), r);
    }

    @Test(enabled = false)
    public void should_invoke_cancel_method_on_future_object() {

        /* Given */
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // NOP
            }
        };
        FutureTask<Void> future = new FutureTask<Void>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return null;
            }
        });
        RequestExecution execution = new RequestExecution(r);

        /* When */
        execution.setFuture(future);
        execution.cancel();
    }
}