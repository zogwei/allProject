package com.sun.btrace.samples.test;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class TracingScript {
   @OnMethod(
      clazz="com.sun.btrace.samples.test.Counter",
      method="add",
      location=@Location(Kind.RETURN)
   )
   public static void traceExecute(int num,@Return int result){
     println("====== ");
     println(strcat("parameter num: ",str(num)));
     println(strcat("return value:",str(result)));
   }
}
