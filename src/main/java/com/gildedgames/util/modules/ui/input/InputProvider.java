package com.gildedgames.util.modules.ui.input;

import com.gildedgames.util.modules.ui.data.rect.Rect;
import com.gildedgames.util.modules.ui.data.rect.RectHolder;

public interface InputProvider
{

	void refreshResolution();

	float getScreenWidth();

	float getScreenHeight();

	float getScaleFactor();

	float getMouseX();

	float getMouseY();

	void setMouseX(float x);

	void setMouseY(float y);

	void setMouse(float x, float y);

	boolean isHovered(Rect dim);

	boolean isHovered(RectHolder holder);

	InputProvider clone();

	InputProvider copyWithMouseXOffset(float xOffset);

	InputProvider copyWithMouseYOffset(float yOffset);

}
