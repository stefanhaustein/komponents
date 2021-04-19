package org.kobjects.komponents.core.grid

import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import org.kobjects.komponents.core.grid.mobile.MeasurementMode
import org.kobjects.komponents.core.grid.mobile.applyGridLayout
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView

class IosGridView(val container: GridLayout) : UIView(CGRectMake(0.0, 0.0, 100.0, 100.0)) {

    @ObjCAction
    fun layoutSubviews() {
        frame.useContents {
            applyGridLayout(
                container,
                container.children,
                MeasurementMode.EXACTLY,
                this.size.width,
                MeasurementMode.EXACTLY,
                this.size.height,
                /* measureOnly= */ false);
        }
    }

}