/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.9
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package org.doubango.tinyWRAP;

public enum tmedia_mode_t {
  tmedia_mode_none,
  tmedia_mode_optional,
  tmedia_mode_mandatory;

  public final int swigValue() {
    return swigValue;
  }

  public static tmedia_mode_t swigToEnum(int swigValue) {
    tmedia_mode_t[] swigValues = tmedia_mode_t.class.getEnumConstants();
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (tmedia_mode_t swigEnum : swigValues)
      if (swigEnum.swigValue == swigValue)
        return swigEnum;
    throw new IllegalArgumentException("No enum " + tmedia_mode_t.class + " with value " + swigValue);
  }

  @SuppressWarnings("unused")
  private tmedia_mode_t() {
    this.swigValue = SwigNext.next++;
  }

  @SuppressWarnings("unused")
  private tmedia_mode_t(int swigValue) {
    this.swigValue = swigValue;
    SwigNext.next = swigValue+1;
  }

  @SuppressWarnings("unused")
  private tmedia_mode_t(tmedia_mode_t swigEnum) {
    this.swigValue = swigEnum.swigValue;
    SwigNext.next = this.swigValue+1;
  }

  private final int swigValue;

  private static class SwigNext {
    private static int next = 0;
  }
}

