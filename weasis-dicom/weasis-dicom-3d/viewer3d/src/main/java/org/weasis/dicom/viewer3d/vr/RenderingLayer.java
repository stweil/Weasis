/*
 * Copyright (c) 2023 Weasis Team and other contributors.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License 2.0 which is available at https://www.eclipse.org/legal/epl-2.0, or the Apache
 * License, Version 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package org.weasis.dicom.viewer3d.vr;

import java.util.ArrayList;
import java.util.List;
import org.weasis.core.api.media.data.ImageElement;
import org.weasis.opencv.op.lut.LutShape;

public class RenderingLayer<E extends ImageElement> {
  private final List<RenderingLayerChangeListener<E>> listenerList;
  protected int depthSampleNumber;
  private int windowWidth;
  private int windowCenter;
  private boolean shading;

  private boolean slicing;
  private int quality;
  private LutShape lutShape;
  private RenderingType renderingType;

  public RenderingLayer() {
    this.listenerList = new ArrayList<>();
    this.depthSampleNumber = 512;
    this.windowWidth = 200;
    this.windowCenter = 40;
    this.shading = false;
    this.quality = 1024;
    this.renderingType = RenderingType.COMPOSITE;
  }

  public void addLayerChangeListener(RenderingLayerChangeListener<E> listener) {
    if (listener != null && !listenerList.contains(listener)) {
      listenerList.add(listener);
    }
  }

  public void removeLayerChangeListener(RenderingLayerChangeListener<E> listener) {
    if (listener != null) {
      listenerList.remove(listener);
    }
  }

  public void fireLayerChanged() {
    for (RenderingLayerChangeListener<E> listener : listenerList) {
      listener.handleLayerChanged(this);
    }
  }

  public void setRenderingType(RenderingType type) {
    if (this.renderingType != type) {
      this.renderingType = type;
      fireLayerChanged();
    }
  }

  public RenderingType getRenderingType() {
    return renderingType;
  }

  public int getWindowWidth() {
    return windowWidth;
  }

  public void setWindowWidth(int windowWidth) {
    if (this.windowWidth != windowWidth) {
      this.windowWidth = windowWidth;
      fireLayerChanged();
    }
  }

  public int getWindowCenter() {
    return windowCenter;
  }

  public void setWindowCenter(int windowCenter) {
    if (this.windowCenter != windowCenter) {
      this.windowCenter = windowCenter;
      fireLayerChanged();
    }
  }

  public boolean isShading() {
    return shading;
  }

  public void setShading(boolean shading) {
    if (this.shading != shading) {
      this.shading = shading;
      fireLayerChanged();
    }
  }

  public boolean isSlicing() {
    return slicing;
  }

  public void setSlicing(boolean slicing) {
    this.slicing = slicing;
  }

  public int getQuality() {
    return quality;
  }

  public void setQuality(int quality) {
    if (this.quality != quality) {
      this.quality = quality;
      fireLayerChanged();
    }
  }

  public int getDepthSampleNumber() {
    return depthSampleNumber;
  }

  public void setDepthSampleNumber(int depthSampleNumber) {
    this.depthSampleNumber = depthSampleNumber;
  }

  public LutShape getLutShape() {
    return lutShape;
  }

  public void setLutShape(LutShape lutShape) {
    if (this.lutShape != lutShape) {
      this.lutShape = lutShape;
      fireLayerChanged();
    }
  }

  public int getLutShapeId() {
    if (lutShape == null) {
      return 0;
    }
    return switch (lutShape.getFunctionType()) {
      case LINEAR -> 0;
      case SIGMOID -> 1;
      case SIGMOID_NORM -> 2;
      case LOG -> 3;
      case LOG_INV -> 4;
    };
  }
}