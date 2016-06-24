/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tomitribe.oss.snapjms.internal.slf4j;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

/**
 * Serializable version of Logger so it can be saved in Session attributes
 */
public class SLogger implements Logger, Serializable {
   private static final long serialVersionUID = 1L;
   private final Class<?> clazz;
   private transient Logger log;

   public SLogger(Class<?> clazz) {
      this.clazz = clazz;
      getLog();
   }

   private Logger getLog() {
      if (log == null) {
         log = LoggerFactory.getLogger(clazz);
      }
      return log;
   }

   public String getName() {
      return getLog().getName();
   }

   public boolean isTraceEnabled() {
      return getLog().isTraceEnabled();
   }

   public void trace(String msg) {
      getLog().trace(msg);
   }

   public void trace(String format, Object arg) {
      getLog().trace(format, arg);
   }

   public void trace(String format, Object arg1, Object arg2) {
      getLog().trace(format, arg1, arg2);
   }

   public void trace(String format, Object... arguments) {
      getLog().trace(format, arguments);
   }

   public void trace(String msg, Throwable t) {
      getLog().trace(msg, t);
   }

   public boolean isTraceEnabled(Marker marker) {
      return getLog().isTraceEnabled(marker);
   }

   public void trace(Marker marker, String msg) {
      getLog().trace(marker, msg);
   }

   public void trace(Marker marker, String format, Object arg) {
      getLog().trace(marker, format, arg);
   }

   public void trace(Marker marker, String format, Object arg1, Object arg2) {
      getLog().trace(marker, format, arg1, arg2);
   }

   public void trace(Marker marker, String format, Object... argArray) {
      getLog().trace(marker, format, argArray);
   }

   public void trace(Marker marker, String msg, Throwable t) {
      getLog().trace(marker, msg, t);
   }

   public boolean isDebugEnabled() {
      return getLog().isDebugEnabled();
   }

   public void debug(String msg) {
      getLog().debug(msg);
   }

   public void debug(String format, Object arg) {
      getLog().debug(format, arg);
   }

   public void debug(String format, Object arg1, Object arg2) {
      getLog().debug(format, arg1, arg2);
   }

   public void debug(String format, Object... arguments) {
      getLog().debug(format, arguments);
   }

   public void debug(String msg, Throwable t) {
      getLog().debug(msg, t);
   }

   public boolean isDebugEnabled(Marker marker) {
      return getLog().isDebugEnabled(marker);
   }

   public void debug(Marker marker, String msg) {
      getLog().debug(marker, msg);
   }

   public void debug(Marker marker, String format, Object arg) {
      getLog().debug(marker, format, arg);
   }

   public void debug(Marker marker, String format, Object arg1, Object arg2) {
      getLog().debug(marker, format, arg1, arg2);
   }

   public void debug(Marker marker, String format, Object... arguments) {
      getLog().debug(marker, format, arguments);
   }

   public void debug(Marker marker, String msg, Throwable t) {
      getLog().debug(marker, msg, t);
   }

   public boolean isInfoEnabled() {
      return getLog().isInfoEnabled();
   }

   public void info(String msg) {
      getLog().info(msg);
   }

   public void info(String format, Object arg) {
      getLog().info(format, arg);
   }

   public void info(String format, Object arg1, Object arg2) {
      getLog().info(format, arg1, arg2);
   }

   public void info(String format, Object... arguments) {
      getLog().info(format, arguments);
   }

   public void info(String msg, Throwable t) {
      getLog().info(msg, t);
   }

   public boolean isInfoEnabled(Marker marker) {
      return getLog().isInfoEnabled(marker);
   }

   public void info(Marker marker, String msg) {
      getLog().info(marker, msg);
   }

   public void info(Marker marker, String format, Object arg) {
      getLog().info(marker, format, arg);
   }

   public void info(Marker marker, String format, Object arg1, Object arg2) {
      getLog().info(marker, format, arg1, arg2);
   }

   public void info(Marker marker, String format, Object... arguments) {
      getLog().info(marker, format, arguments);
   }

   public void info(Marker marker, String msg, Throwable t) {
      getLog().info(marker, msg, t);
   }

   public boolean isWarnEnabled() {
      return getLog().isWarnEnabled();
   }

   public void warn(String msg) {
      getLog().warn(msg);
   }

   public void warn(String format, Object arg) {
      getLog().warn(format, arg);
   }

   public void warn(String format, Object... arguments) {
      getLog().warn(format, arguments);
   }

   public void warn(String format, Object arg1, Object arg2) {
      getLog().warn(format, arg1, arg2);
   }

   public void warn(String msg, Throwable t) {
      getLog().warn(msg, t);
   }

   public boolean isWarnEnabled(Marker marker) {
      return getLog().isWarnEnabled(marker);
   }

   public void warn(Marker marker, String msg) {
      getLog().warn(marker, msg);
   }

   public void warn(Marker marker, String format, Object arg) {
      getLog().warn(marker, format, arg);
   }

   public void warn(Marker marker, String format, Object arg1, Object arg2) {
      getLog().warn(marker, format, arg1, arg2);
   }

   public void warn(Marker marker, String format, Object... arguments) {
      getLog().warn(marker, format, arguments);
   }

   public void warn(Marker marker, String msg, Throwable t) {
      getLog().warn(marker, msg, t);
   }

   public boolean isErrorEnabled() {
      return getLog().isErrorEnabled();
   }

   public void error(String msg) {
      getLog().error(msg);
   }

   public void error(String format, Object arg) {
      getLog().error(format, arg);
   }

   public void error(String format, Object arg1, Object arg2) {
      getLog().error(format, arg1, arg2);
   }

   public void error(String format, Object... arguments) {
      getLog().error(format, arguments);
   }

   public void error(String msg, Throwable t) {
      getLog().error(msg, t);
   }

   public boolean isErrorEnabled(Marker marker) {
      return getLog().isErrorEnabled(marker);
   }

   public void error(Marker marker, String msg) {
      getLog().error(marker, msg);
   }

   public void error(Marker marker, String format, Object arg) {
      getLog().error(marker, format, arg);
   }

   public void error(Marker marker, String format, Object arg1, Object arg2) {
      getLog().error(marker, format, arg1, arg2);
   }

   public void error(Marker marker, String format, Object... arguments) {
      getLog().error(marker, format, arguments);
   }

   public void error(Marker marker, String msg, Throwable t) {
      getLog().error(marker, msg, t);
   }
}
