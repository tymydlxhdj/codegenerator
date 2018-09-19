package com.mqfdy.code.designer.editor.form;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEffect;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.ILayoutExtension;
import org.eclipse.ui.forms.widgets.SizeCache;
import org.eclipse.ui.forms.widgets.Twistie;
import org.eclipse.ui.internal.forms.IMessageToolTipManager;
import org.eclipse.ui.internal.forms.widgets.BusyIndicator;
import org.eclipse.ui.internal.forms.widgets.FormUtil;

// TODO: Auto-generated Javadoc
/**
 * Form heading title.
 *
 * @author mqfdy
 */
public class TitleRegion extends Canvas {
	
	/** The Constant STATE_NORMAL. */
	public static final int STATE_NORMAL = 0;
	
	/** The Constant STATE_HOVER_LIGHT. */
	public static final int STATE_HOVER_LIGHT = 1;
	
	/** The Constant STATE_HOVER_FULL. */
	public static final int STATE_HOVER_FULL = 2;
	
	/** The hover state. */
	private int hoverState;
	
	/** The Constant HMARGIN. */
	private static final int HMARGIN = 1;
	
	/** The Constant VMARGIN. */
	private static final int VMARGIN = 5;
	
	/** The Constant SPACING. */
	private static final int SPACING = 5;
	
	/** The Constant ARC_WIDTH. */
	private static final int ARC_WIDTH = 20;
	
	/** The Constant ARC_HEIGHT. */
	private static final int ARC_HEIGHT = 20;
	
	/** The image. */
	private Image image;
	
	/** The busy label. */
	private BusyIndicator busyLabel;
	
	/** The title label. */
	private Label titleLabel;
	
	/** The title cache. */
	private SizeCache titleCache;
	
	/** The font height. */
	private int fontHeight = -1;
	
	/** The font baseline height. */
	private int fontBaselineHeight = -1;
	
	/** The menu hyperlink. */
	private MenuHyperlink menuHyperlink;
	
	/** The menu manager. */
	private MenuManager menuManager;
	
	/** The drag support. */
	private boolean dragSupport;
	
	/** The drag operations. */
	private int dragOperations;
	
	/** The drag transfer types. */
	private Transfer[] dragTransferTypes;
	
	/** The drag listener. */
	private DragSourceListener dragListener;
	
	/** The drag source. */
	private DragSource dragSource;
	
	/** The drag image. */
	private Image dragImage;

	/**
	 * The listener interface for receiving hover events. The class that is
	 * interested in processing a hover event implements this interface, and the
	 * object created with that class is registered with a component using the
	 * component's <code>addHoverListener<code> method. When the hover event
	 * occurs, that object's appropriate method is invoked.
	 *
	 * @see HoverEvent
	 */
	private class HoverListener implements MouseTrackListener,
			MouseMoveListener {

		/**
		 * Mouse enter.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		public void mouseEnter(MouseEvent e) {
			setHoverState(STATE_HOVER_FULL);
		}

		/**
		 * Mouse exit.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		public void mouseExit(MouseEvent e) {
			setHoverState(STATE_NORMAL);
		}

		/**
		 * Mouse hover.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		public void mouseHover(MouseEvent e) {
		}

		/**
		 * Mouse move.
		 *
		 * @author mqfdy
		 * @param e
		 *            the e
		 * @Date 2018-09-03 09:00
		 */
		public void mouseMove(MouseEvent e) {
			if (e.button > 0)
				setHoverState(STATE_NORMAL);
			else
				setHoverState(STATE_HOVER_FULL);
		}
	}

	/**
	 * The Class MenuHyperlink.
	 *
	 * @author mqfdy
	 */
	private class MenuHyperlink extends Twistie {
		
		/** The first time. */
		private boolean firstTime = true;

		/**
		 * Instantiates a new menu hyperlink.
		 *
		 * @param parent
		 *            the parent
		 * @param style
		 *            the style
		 */
		public MenuHyperlink(Composite parent, int style) {
			super(parent, style);
			setExpanded(true);
		}

		/**
		 * Sets the expanded.
		 *
		 * @author mqfdy
		 * @param expanded
		 *            the new expanded
		 * @Date 2018-09-03 09:00
		 */
		public void setExpanded(boolean expanded) {
			if (firstTime) {
				super.setExpanded(expanded);
				firstTime = false;
			} else {
				Menu menu = menuManager.createContextMenu(menuHyperlink);
				menu.setVisible(true);
			}
		}
	}

	/**
	 * The Class TitleRegionLayout.
	 *
	 * @author mqfdy
	 */
	private class TitleRegionLayout extends Layout implements ILayoutExtension {

		/**
		 * Compute size.
		 *
		 * @author mqfdy
		 * @param composite
		 *            the composite
		 * @param wHint
		 *            the w hint
		 * @param hHint
		 *            the h hint
		 * @param flushCache
		 *            the flush cache
		 * @return the point
		 * @Date 2018-09-03 09:00
		 */
		protected Point computeSize(Composite composite, int wHint, int hHint,
				boolean flushCache) {
			return layout(composite, false, 0, 0, wHint, hHint, flushCache);
		}

		/**
		 * Layout.
		 *
		 * @author mqfdy
		 * @param composite
		 *            the composite
		 * @param flushCache
		 *            the flush cache
		 * @Date 2018-09-03 09:00
		 */
		protected void layout(Composite composite, boolean flushCache) {
			Rectangle carea = composite.getClientArea();
			layout(composite, true, carea.x, carea.y, carea.width,
					carea.height, flushCache);
		}

		/**
		 * Layout.
		 *
		 * @author mqfdy
		 * @param composite
		 *            the composite
		 * @param move
		 *            the move
		 * @param x
		 *            the x
		 * @param y
		 *            the y
		 * @param width
		 *            the width
		 * @param height
		 *            the height
		 * @param flushCache
		 *            the flush cache
		 * @return the point
		 * @Date 2018-09-03 09:00
		 */
		private Point layout(Composite composite, boolean move, int x, int y,
				int width, int height, boolean flushCache) {
			int iwidth = width == SWT.DEFAULT ? SWT.DEFAULT : width - HMARGIN
					* 2;
			Point bsize = null;
			Point tsize = null;
			Point msize = null;

			if (busyLabel != null) {
				bsize = busyLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
			if (menuManager != null) {
				menuHyperlink.setVisible(!menuManager.isEmpty()
						&& titleLabel.getVisible());
				if (menuHyperlink.getVisible())
					msize = menuHyperlink.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
			if (flushCache)
				titleCache.flush();
			titleCache.setControl(titleLabel);
			int twidth = iwidth == SWT.DEFAULT ? iwidth : iwidth - SPACING * 2;
			if (bsize != null && twidth != SWT.DEFAULT)
				twidth -= bsize.x + SPACING;
			if (msize != null && twidth != SWT.DEFAULT)
				twidth -= msize.x + SPACING;
			if (titleLabel.getVisible()) {
				tsize = titleCache.computeSize(twidth, SWT.DEFAULT);
				if (twidth != SWT.DEFAULT) {
					// correct for the case when width hint is larger
					// than the maximum width - this is when the text
					// can be rendered on one line with width to spare
					int maxWidth = titleCache.computeSize(SWT.DEFAULT,
							SWT.DEFAULT).x;
					tsize.x = Math.min(tsize.x, maxWidth);
				}
			} else
				tsize = new Point(0, 0);
			Point size = new Point(width, height);
			if (!move) {
				// compute size
				size.x = tsize.x > 0 ? HMARGIN * 2 + SPACING * 2 + tsize.x : 0;
				size.y = tsize.y;
				if (bsize != null) {
					size.x += bsize.x + SPACING;
					size.y = Math.max(size.y, bsize.y);
				}
				if (msize != null) {
					size.x += msize.x + SPACING;
					size.y = Math.max(size.y, msize.y);
				}
				if (size.y > 0)
					size.y += VMARGIN * 2;
			} else {
				// position controls
				int xloc = x + HMARGIN + SPACING;
				int yloc = y + VMARGIN;
				if (bsize != null) {
					busyLabel.setBounds(xloc,
							// yloc + height / 2 - bsize.y / 2,
							yloc + (getFontHeight() >= bsize.y ? getFontHeight() : bsize.y) - 1 - bsize.y,
							bsize.x, bsize.y);
					xloc += bsize.x + SPACING;
				}
				if (titleLabel.getVisible()) {
					int tw = width - HMARGIN * 2 - SPACING * 2;
					String os = System.getProperty("os.name"); //$NON-NLS-1$
					if (Constants.OS_LINUX.equalsIgnoreCase(os)) {
						tw += 1; // See Bug 342610
					}
					if (bsize != null)
						tw -= bsize.x + SPACING;
					if (msize != null)
						tw -= msize.x + SPACING;
					titleLabel.setBounds(xloc,
					// yloc + height / 2 - tsize.y / 2,
							yloc, tw, tsize.y);
					xloc += tw + SPACING;
				}
				if (msize != null) {
					menuHyperlink.setBounds(xloc, yloc
							+ getFontHeight() / 2 - msize.y / 2,
							msize.x, msize.y);
				}
			}
			return size;
		}

		/**
		 * Compute maximum width.
		 *
		 * @author mqfdy
		 * @param parent
		 *            the parent
		 * @param changed
		 *            the changed
		 * @return the int
		 * @Date 2018-09-03 09:00
		 */
		public int computeMaximumWidth(Composite parent, boolean changed) {
			return computeSize(parent, SWT.DEFAULT, SWT.DEFAULT, changed).x;
		}

		/**
		 * Compute minimum width.
		 *
		 * @author mqfdy
		 * @param parent
		 *            the parent
		 * @param changed
		 *            the changed
		 * @return the int
		 * @Date 2018-09-03 09:00
		 */
		public int computeMinimumWidth(Composite parent, boolean changed) {
			return computeSize(parent, 0, SWT.DEFAULT, changed).x;
		}
	}

	/**
	 * Instantiates a new title region.
	 *
	 * @param parent
	 *            the parent
	 */
	public TitleRegion(Composite parent) {
		super(parent, SWT.NULL);
		titleLabel = new Label(this, SWT.None);
		titleLabel.setVisible(false);
		titleCache = new SizeCache();
		super.setLayout(new TitleRegionLayout());
		hookHoverListeners();
		addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event e) {
				if (dragImage != null) {
					dragImage.dispose();
					dragImage = null;
				}
			}
		});
	}

	/**
	 * @return
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#forceFocus()
	 */
	public boolean forceFocus() {
		return false;
	}

	/**
	 * Gets the color.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return the color
	 * @Date 2018-09-03 09:00
	 */
	private Color getColor(String key) {
		return (Color) ((FormHeading) getParent()).colors.get(key);
	}

	/**
	 * Hook hover listeners.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void hookHoverListeners() {
		HoverListener listener = new HoverListener();
		addMouseTrackListener(listener);
		addMouseMoveListener(listener);
		titleLabel.addMouseTrackListener(listener);
		titleLabel.addMouseMoveListener(listener);
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				onPaint(e);
			}
		});
	}

	/**
	 * On paint.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	private void onPaint(PaintEvent e) {
		if (hoverState == STATE_NORMAL)
			return;
		GC gc = e.gc;
		Rectangle carea = getClientArea();
		gc.setBackground(getHoverBackground());
		int savedAntialias = gc.getAntialias();
		FormUtil.setAntialias(gc, SWT.ON);
		gc.fillRoundRectangle(carea.x + HMARGIN, carea.y + 2, carea.width
				- HMARGIN * 2, carea.height - 4, ARC_WIDTH, ARC_HEIGHT);
		FormUtil.setAntialias(gc, savedAntialias);
	}

	/**
	 * Gets the hover background.
	 *
	 * @author mqfdy
	 * @return the hover background
	 * @Date 2018-09-03 09:00
	 */
	private Color getHoverBackground() {
		if (hoverState == STATE_NORMAL)
			return null;
		Color color = getColor(hoverState == STATE_HOVER_FULL ? IFormColors.H_HOVER_FULL
				: IFormColors.H_HOVER_LIGHT);
		if (color == null)
			color = getDisplay()
					.getSystemColor(
							hoverState == STATE_HOVER_FULL ? SWT.COLOR_WIDGET_BACKGROUND
									: SWT.COLOR_WIDGET_LIGHT_SHADOW);
		return color;
	}

	/**
	 * Sets the hover state.
	 *
	 * @author mqfdy
	 * @param state
	 *            the new hover state
	 * @Date 2018-09-03 09:00
	 */
	public void setHoverState(int state) {
		if (dragSource == null || this.hoverState == state)
			return;
		this.hoverState = state;
		Color color = getHoverBackground();
		titleLabel.setBackground(color != null ? color
				: getColor(FormHeading.COLOR_BASE_BG));
		if (busyLabel != null)
			busyLabel.setBackground(color != null ? color
					: getColor(FormHeading.COLOR_BASE_BG));
		if (menuHyperlink != null)
			menuHyperlink.setBackground(color != null ? color
					: getColor(FormHeading.COLOR_BASE_BG));
		redraw();
	}

	/**
	 * Fully delegates the size computation to the internal layout manager.
	 *
	 * @author mqfdy
	 * @param wHint
	 *            the w hint
	 * @param hHint
	 *            the h hint
	 * @param changed
	 *            the changed
	 * @return the point
	 * @Date 2018-09-03 09:00
	 */
	public final Point computeSize(int wHint, int hHint, boolean changed) {
		return ((TitleRegionLayout) getLayout()).computeSize(this, wHint,
				hHint, changed);
	}

	/**
	 * Sets the layout.
	 *
	 * @author mqfdy
	 * @param layout
	 *            the new layout
	 * @Date 2018-09-03 09:00
	 */
	public final void setLayout(Layout layout) {
		// do nothing
	}

	/**
	 * Gets the image.
	 *
	 * @author mqfdy
	 * @return the image
	 * @Date 2018-09-03 09:00
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @author mqfdy
	 * @param image
	 *            the new image
	 * @Date 2018-09-03 09:00
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Update image.
	 *
	 * @author mqfdy
	 * @param newImage
	 *            the new image
	 * @param doLayout
	 *            the do layout
	 * @Date 2018-09-03 09:00
	 */
	public void updateImage(Image newImage, boolean doLayout) {
		Image theImage = newImage != null ? newImage : this.image;

		if (theImage != null) {
			ensureBusyLabelExists();
		} else if (busyLabel != null) {
			if (!busyLabel.isBusy()) {
				busyLabel.dispose();
				busyLabel = null;
			}
		}
		if (busyLabel != null) {
			busyLabel.setImage(theImage);
		}
		if (doLayout)
			layout();
	}

	/**
	 * Update tool tip.
	 *
	 * @author mqfdy
	 * @param toolTip
	 *            the tool tip
	 * @Date 2018-09-03 09:00
	 */
	public void updateToolTip(String toolTip) {
		if (busyLabel != null)
			busyLabel.setToolTipText(toolTip);
	}

	/**
	 * Sets the background.
	 *
	 * @author mqfdy
	 * @param bg
	 *            the new background
	 * @Date 2018-09-03 09:00
	 */
	public void setBackground(Color bg) {
		super.setBackground(bg);
		titleLabel.setBackground(bg);
		if (busyLabel != null)
			busyLabel.setBackground(bg);
		if (menuHyperlink != null)
			menuHyperlink.setBackground(bg);
	}

	/**
	 * Sets the foreground.
	 *
	 * @author mqfdy
	 * @param fg
	 *            the new foreground
	 * @Date 2018-09-03 09:00
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		titleLabel.setForeground(fg);
		if (menuHyperlink != null)
			menuHyperlink.setForeground(fg);
	}

	/**
	 * Sets the text.
	 *
	 * @author mqfdy
	 * @param text
	 *            the new text
	 * @Date 2018-09-03 09:00
	 */
	public void setText(String text) {
		if (text != null)
			titleLabel.setText(text);
		titleLabel.setVisible(text != null);
		layout();
		redraw();
	}

	/**
	 * Gets the text.
	 *
	 * @author mqfdy
	 * @return the text
	 * @Date 2018-09-03 09:00
	 */
	public String getText() {
		return titleLabel.getText();
	}

	/**
	 * Sets the font.
	 *
	 * @author mqfdy
	 * @param font
	 *            the new font
	 * @Date 2018-09-03 09:00
	 */
	public void setFont(Font font) {
		super.setFont(font);
		titleLabel.setFont(font);
		fontHeight = -1;
		fontBaselineHeight = -1;
		layout();
	}

	/**
	 * Ensure busy label exists.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void ensureBusyLabelExists() {
		if (busyLabel == null) {
			busyLabel = new BusyIndicator(this, SWT.NULL);
			busyLabel.setBackground(getColor(FormHeading.COLOR_BASE_BG));
			HoverListener listener = new HoverListener();
			busyLabel.addMouseTrackListener(listener);
			busyLabel.addMouseMoveListener(listener);
			if (menuManager != null)
				busyLabel.setMenu(menuManager.createContextMenu(this));
			if (dragSupport)
				addDragSupport(busyLabel, dragOperations, dragTransferTypes, dragListener);
			IMessageToolTipManager mng = ((FormHeading) getParent())
					.getMessageToolTipManager();
			if (mng != null)
				mng.createToolTip(busyLabel, true);
		}
	}

	/**
	 * Creates the menu hyperlink.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void createMenuHyperlink() {
		menuHyperlink = new MenuHyperlink(this, SWT.NULL);
		menuHyperlink.setBackground(getColor(FormHeading.COLOR_BASE_BG));
		menuHyperlink.setDecorationColor(getForeground());
		menuHyperlink.setHoverDecorationColor(getDisplay().getSystemColor(SWT.COLOR_LIST_FOREGROUND));
		HoverListener listener = new HoverListener();
		menuHyperlink.addMouseTrackListener(listener);
		menuHyperlink.addMouseMoveListener(listener);
		if (dragSupport)
			addDragSupport(menuHyperlink, dragOperations, dragTransferTypes, dragListener);
	}

	/**
	 * Sets the form's busy state. Busy form will display 'busy' animation in
	 * the area of the title image.
	 *
	 * @author mqfdy
	 * @param busy
	 *            the form's busy state
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */

	public boolean setBusy(boolean busy) {
		if (busy)
			ensureBusyLabelExists();
		else if (busyLabel == null)
			return false;
		if (busy == busyLabel.isBusy())
			return false;
		busyLabel.setBusy(busy);
		if (busyLabel.getImage() == null) {
			layout();
			return true;
		}
		return false;
	}

	/**
	 * Checks if is busy.
	 *
	 * @author mqfdy
	 * @return true, if is busy
	 * @Date 2018-09-03 09:00
	 */
	public boolean isBusy() {
		return busyLabel != null && busyLabel.isBusy();
	}

	/**
	 * Gets the font height.
	 *
	 * @author mqfdy
	 * @return the font height
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * Returns the complete height of the font.
	 */
	public int getFontHeight() {
		if (fontHeight == -1) {
			Font font = getFont();
			GC gc = new GC(getDisplay());
			gc.setFont(font);
			FontMetrics fm = gc.getFontMetrics();
			fontHeight = fm.getHeight();
			gc.dispose();
		}
		return fontHeight;
	}

	/**
	 * Gets the font baseline height.
	 *
	 * @author mqfdy
	 * @return the font baseline height
	 * @Date 2018-09-03 09:00
	 */
	/*
	 * Returns the height of the font starting at the baseline,
	 * i.e. without the descent.
	 */
	public int getFontBaselineHeight() {
		if (fontBaselineHeight == -1) {
			Font font = getFont();
			GC gc = new GC(getDisplay());
			gc.setFont(font);
			FontMetrics fm = gc.getFontMetrics();
			fontBaselineHeight = fm.getHeight() - fm.getDescent();
			gc.dispose();
		}
		return fontBaselineHeight;
	}

	/**
	 * Gets the menu manager.
	 *
	 * @author mqfdy
	 * @return the menu manager
	 * @Date 2018-09-03 09:00
	 */
	public IMenuManager getMenuManager() {
		if (menuManager == null) {
			menuManager = new MenuManager();
			Menu menu = menuManager.createContextMenu(this);
			setMenu(menu);
			titleLabel.setMenu(menu);
			if (busyLabel != null)
				busyLabel.setMenu(menu);
			createMenuHyperlink();
		}
		return menuManager;
	}

	/**
	 * Adds the drag support.
	 *
	 * @author mqfdy
	 * @param operations
	 *            the operations
	 * @param transferTypes
	 *            the transfer types
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addDragSupport(int operations, Transfer[] transferTypes,
			DragSourceListener listener) {
		dragSupport = true;
		dragOperations = operations;
		dragTransferTypes = transferTypes;
		dragListener = listener;
		dragSource = addDragSupport(titleLabel, operations, transferTypes,
				listener);
		addDragSupport(this, operations, transferTypes, listener);
		if (busyLabel != null)
			addDragSupport(busyLabel, operations, transferTypes, listener);
		if (menuHyperlink != null)
			addDragSupport(menuHyperlink, operations, transferTypes, listener);
	}

	/**
	 * Adds the drag support.
	 *
	 * @author mqfdy
	 * @param control
	 *            the control
	 * @param operations
	 *            the operations
	 * @param transferTypes
	 *            the transfer types
	 * @param listener
	 *            the listener
	 * @return the drag source
	 * @Date 2018-09-03 09:00
	 */
	private DragSource addDragSupport(Control control, int operations,
			Transfer[] transferTypes, DragSourceListener listener) {
		DragSource source = new DragSource(control, operations);
		source.setTransfer(transferTypes);
		source.addDragListener(listener);
		source.setDragSourceEffect(new DragSourceEffect(control) {
			public void dragStart(DragSourceEvent event) {
				event.image = createDragEffectImage();
			}
		});
		return source;
	}

	/**
	 * Creates the drag effect image.
	 *
	 * @author mqfdy
	 * @return the image
	 * @Date 2018-09-03 09:00
	 */
	private Image createDragEffectImage() {
		/*
		 * if (dragImage != null) { dragImage.dispose(); } GC gc = new GC(this);
		 * Point size = getSize(); dragImage = new Image(getDisplay(), size.x,
		 * size.y); gc.copyArea(dragImage, 0, 0); gc.dispose(); return
		 * dragImage;
		 */
		return null;
	}

	/**
	 * Adds the drop support.
	 *
	 * @author mqfdy
	 * @param operations
	 *            the operations
	 * @param transferTypes
	 *            the transfer types
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addDropSupport(int operations, Transfer[] transferTypes,
			DropTargetListener listener) {
		final DropTarget target = new DropTarget(this, operations);
		target.setTransfer(transferTypes);
		target.addDropListener(listener);
	}
}