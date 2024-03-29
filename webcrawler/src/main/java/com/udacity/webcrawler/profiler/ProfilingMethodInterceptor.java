package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.time.Duration;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

  private final Clock clock;
  private final Object obj;
  private final ProfilingState profileStateObj;

  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Clock clock, Object obj, ProfilingState profileStateObj) {
    this.clock = Objects.requireNonNull(clock);
    this.obj = Objects.requireNonNull(obj);
    this.profileStateObj = Objects.requireNonNull(profileStateObj);
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // TODO: This method interceptor should inspect the called method to see if it is a profiled
    //       method. For profiled methods, the interceptor should record the start time, then
    //       invoke the method using the object that is being profiled. Finally, for profiled
    //       methods, the interceptor should record how long the method call took, using the
    //       ProfilingState methods.
    Object retVal;
    if (method.isAnnotationPresent(Profiled.class)) {
        Instant start = clock.instant();
        retVal = method.invoke(obj, args);
        Instant end = clock.instant();
        Duration duration = Duration.between(start, end);

        profileStateObj.record(obj.getClass(), method, duration);

    } else {
        retVal = method.invoke(obj, args);
    }

    return retVal;
  }
}
