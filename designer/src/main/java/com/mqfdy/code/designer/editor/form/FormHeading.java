package com.mqfdy.code.designer.editor.form;

import java.util.Hashtable;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
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
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IMessage;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ILayoutExtension;
import org.eclipse.ui.forms.widgets.SizeCache;
import org.eclipse.ui.internal.forms.IMessageToolTipManager;
import org.eclipse.ui.internal.forms.MessageManager;
import org.eclipse.ui.internal.forms.widgets.FormImages;
import org.eclipse.ui.internal.forms.widgets.FormsResources;

// TODO: Auto-generated Javadoc
/**
 * Form header moved out of the form class.
 *
 * @author mqfdy
 */
@SuppressWarnings("restriction")
public class FormHeading extends Canvas {
	
	/** The Constant TITLE_HMARGIN. */
	private static final int TITLE_HMARGIN = 1;
	
	/** The Constant SPACING. */
	private static final int SPACING = 5;
	
	/** The Constant VSPACING. */
	private static final int VSPACING = 5;
	
	/** The Constant HMARGIN. */
	private static final int HMARGIN = 6;
	
	/** The Constant VMARGIN. */
	private static final int VMARGIN = 1;
	
	/** The Constant CLIENT_MARGIN. */
	private static final int CLIENT_MARGIN = 1;

	/** The Constant SEPARATOR. */
	private static final int SEPARATOR = 1 << 1;
	
	/** The Constant BOTTOM_TOOLBAR. */
	private static final int BOTTOM_TOOLBAR = 1 << 2;
	
	/** The Constant BACKGROUND_IMAGE_TILED. */
	private static final int BACKGROUND_IMAGE_TILED = 1 << 3;
	
	/** The Constant SEPARATOR_HEIGHT. */
	private static final int SEPARATOR_HEIGHT = 2;
	
	/** The Constant MESSAGE_AREA_LIMIT. */
	private static final int MESSAGE_AREA_LIMIT = 50;
	
	/** The null message array. */
	static IMessage[] NULL_MESSAGE_ARRAY = new IMessage[] {};

	/** The Constant COLOR_BASE_BG. */
	public static final String COLOR_BASE_BG = "baseBg"; //$NON-NLS-1$

	/** The background image. */
	private Image backgroundImage;

	/** The gradient image. */
	private Image gradientImage;

	/** The colors. */
	Hashtable colors = new Hashtable();

	/** The flags. */
	private int flags;

	/** The gradient info. */
	private GradientInfo gradientInfo;

	/** The tool bar manager. */
	private ToolBarManager toolBarManager;

	/** The toolbar cache. */
	private SizeCache toolbarCache = new SizeCache();

	/** The client cache. */
	private SizeCache clientCache = new SizeCache();

	/** The message cache. */
	private SizeCache messageCache = new SizeCache();

	/** The title region. */
	private TitleRegion titleRegion;

	/** The message region. */
	private MessageRegion messageRegion;

	/** The message tool tip manager. */
	private IMessageToolTipManager messageToolTipManager = new DefaultMessageToolTipManager();

	/** The head client. */
	private Control headClient;

	/**
	 * The Class DefaultMessageToolTipManager.
	 *
	 * @author mqfdy
	 */
	private class DefaultMessageToolTipManager implements
			IMessageToolTipManager {
		
		/**
		 * Creates the tool tip.
		 *
		 * @author mqfdy
		 * @param control
		 *            the control
		 * @param imageLabel
		 *            the image label
		 * @Date 2018-09-03 09:00
		 */
		public void createToolTip(Control control, boolean imageLabel) {
		}

		/**
		 * 
		 */
		public void update() {
			String details = getMessageType() == 0 ? null : MessageManager
					.createDetails(getChildrenMessages());
			if (messageRegion != null)
				messageRegion.updateToolTip(details);
			if (getMessageType() > 0
					&& (details == null || details.length() == 0))
				details = getMessage();
			titleRegion.updateToolTip(details);
		}
	}

	/**
	 * The Class GradientInfo.
	 *
	 * @author mqfdy
	 */
	private class GradientInfo {
		
		/** The gradient colors. */
		Color[] gradientColors;

		/** The percents. */
		int[] percents;

		/** The vertical. */
		boolean vertical;
	}

	/**
	 * The Class FormHeadingLayout.
	 *
	 * @author mqfdy
	 */
	private class FormHeadingLayout extends Layout implements ILayoutExtension {
		
		/**
		 * Compute minimum width.
		 *
		 * @author mqfdy
		 * @param composite
		 *            the composite
		 * @param flushCache
		 *            the flush cache
		 * @return the int
		 * @Date 2018-09-03 09:00
		 */
		public int computeMinimumWidth(Composite composite, boolean flushCache) {
			return computeSize(composite, 5, SWT.DEFAULT, flushCache).x;
		}

		/**
		 * Compute maximum width.
		 *
		 * @author mqfdy
		 * @param composite
		 *            the composite
		 * @param flushCache
		 *            the flush cache
		 * @return the int
		 * @Date 2018-09-03 09:00
		 */
		public int computeMaximumWidth(Composite composite, boolean flushCache) {
			return computeSize(composite, SWT.DEFAULT, SWT.DEFAULT, flushCache).x;
		}

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
		public Point computeSize(Composite composite, int wHint, int hHint,
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
			Rectangle rect = composite.getClientArea();
			layout(composite, true, rect.x, rect.y, rect.width, rect.height,
					flushCache);
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
			Point tsize = null;
			Point msize = null;
			Point tbsize = null;
			Point clsize = null;

			if (flushCache) {
				clientCache.flush();
				messageCache.flush();
				toolbarCache.flush();
			}
			if (hasToolBar()) {
				ToolBar tb = toolBarManager.getControl();
				toolbarCache.setControl(tb);
				tbsize = toolbarCache.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
			if (headClient != null) {
				clientCache.setControl(headClient);
				int cwhint = width;
				if (cwhint != SWT.DEFAULT) {
					cwhint -= HMARGIN * 2;
					if (tbsize != null && getToolBarAlignment() == SWT.BOTTOM)
						cwhint -= tbsize.x + SPACING;
				}
				clsize = clientCache.computeSize(cwhint, SWT.DEFAULT);
			}
			int totalFlexWidth = width;
			int flexWidth = totalFlexWidth;
			if (totalFlexWidth != SWT.DEFAULT) {
				totalFlexWidth -= TITLE_HMARGIN * 2;
				// complete right margin
				if (hasToolBar() && getToolBarAlignment() == SWT.TOP
						|| hasMessageRegion())
					totalFlexWidth -= SPACING;
				// subtract tool bar
				if (hasToolBar() && getToolBarAlignment() == SWT.TOP && tbsize!=null)
					totalFlexWidth -= tbsize.x + SPACING;
				flexWidth = totalFlexWidth;
				if (hasMessageRegion()) {
					// remove message region spacing and divide by 2
					flexWidth -= SPACING;
					// flexWidth /= 2;
				}
			}
			/*
			 * // compute text and message sizes tsize =
			 * titleRegion.computeSize(flexWidth, SWT.DEFAULT); if (flexWidth !=
			 * SWT.DEFAULT && tsize.x < flexWidth) flexWidth += flexWidth -
			 * tsize.x;
			 * 
			 * if (hasMessageRegion()) {
			 * messageCache.setControl(messageRegion.getMessageControl()); msize =
			 * messageCache.computeSize(flexWidth, SWT.DEFAULT); int maxWidth =
			 * messageCache.computeSize(SWT.DEFAULT, SWT.DEFAULT).x; if
			 * (maxWidth < msize.x) { msize.x = maxWidth; // recompute title
			 * with the reclaimed width int tflexWidth = totalFlexWidth -
			 * SPACING - msize.x; tsize = titleRegion.computeSize(tflexWidth,
			 * SWT.DEFAULT); } }
			 */
			if (!hasMessageRegion()) {
				tsize = titleRegion.computeSize(flexWidth, SWT.DEFAULT);
			} else {
				// Total flexible area in the first row is flexWidth.
				// Try natural widths of title and
				Point tsizeNatural = titleRegion.computeSize(SWT.DEFAULT,
						SWT.DEFAULT);
				messageCache.setControl(messageRegion.getMessageControl());
				Point msizeNatural = messageCache.computeSize(SWT.DEFAULT,
						SWT.DEFAULT);
				// try to fit all
				tsize = tsizeNatural;
				msize = msizeNatural;
				if (flexWidth != SWT.DEFAULT) {
					int needed = tsizeNatural.x + msizeNatural.x;
					if (needed > flexWidth) {
						// too big - try to limit the message
						int mwidth = flexWidth - tsizeNatural.x;
						if (mwidth >= MESSAGE_AREA_LIMIT) {
							msize.x = mwidth;
						} else {
							// message is squeezed to the limit
							int flex = flexWidth - MESSAGE_AREA_LIMIT;
							tsize = titleRegion.computeSize(flex, SWT.DEFAULT);
							msize.x = MESSAGE_AREA_LIMIT;
						}
					}
				}
			}

			Point size = new Point(width, height);
			if (!move) {
				// compute sizes
				int width1 = 2 * TITLE_HMARGIN;
				width1 += tsize.x;
				if (msize != null)
					width1 += SPACING + msize.x;
				if (tbsize != null && getToolBarAlignment() == SWT.TOP)
					width1 += SPACING + tbsize.x;
				if (msize != null
						|| (tbsize != null && getToolBarAlignment() == SWT.TOP))
					width1 += SPACING;
				size.x = width1;
				if (clsize != null) {
					int width2 = clsize.x;
					if (tbsize != null && getToolBarAlignment() == SWT.BOTTOM)
						width2 += SPACING + tbsize.x;
					width2 += 2 * HMARGIN;
					size.x = Math.max(width1, width2);
				}
				// height, first row
				size.y = tsize.y;
				if (msize != null)
					size.y = Math.max(msize.y, size.y);
				if (tbsize != null && getToolBarAlignment() == SWT.TOP)
					size.y = Math.max(tbsize.y, size.y);
				if (size.y > 0)
					size.y += VMARGIN * 2;
				// add second row
				int height2 = 0;
				if (tbsize != null && getToolBarAlignment() == SWT.BOTTOM)
					height2 = tbsize.y;
				if (clsize != null)
					height2 = Math.max(height2, clsize.y);
				if (height2 > 0)
					size.y += VSPACING + height2 + CLIENT_MARGIN;
				// add separator
				if (size.y > 0 && isSeparatorVisible())
					size.y += SEPARATOR_HEIGHT;
			} else {
				// position controls
				int xloc = x;
				int yloc = y + VMARGIN;
				int row1Height = tsize.y;
				if (hasMessageRegion()){
					if(msize != null)
						row1Height = Math.max(row1Height, msize.y);
					else
						row1Height = row1Height;
				}
				if (hasToolBar() && getToolBarAlignment() == SWT.TOP){
					if(tbsize != null)
						row1Height = Math.max(row1Height, tbsize.y);
					else
						row1Height = row1Height;
				}
				titleRegion.setBounds(xloc,
				// yloc + row1Height / 2 - tsize.y / 2,
						yloc, tsize.x, tsize.y);
				xloc += tsize.x;

				if (hasMessageRegion()) {
					xloc += SPACING;
					int messageOffset = 0;
					if (tsize.y > 0) {
						// space between title area and title text
						int titleLeadingSpace = (tsize.y - titleRegion.getFontHeight()) / 2;
						// space between message control and message text
						int messageLeadingSpace = 0;
						if(msize!=null && messageRegion!=null){
							messageLeadingSpace =(msize.y - messageRegion.getFontHeight()) / 2;
						}
						// how much to offset the message so baselines align
						messageOffset = (titleLeadingSpace + titleRegion.getFontBaselineHeight())
							- (messageLeadingSpace + messageRegion.getFontBaselineHeight());
					}

					if(msize!=null){
						messageRegion
								.getMessageControl()
								.setBounds(
										xloc,
										tsize.y > 0 ? (yloc + messageOffset)
												: (yloc + row1Height / 2 - msize.y / 2),
										msize.x, msize.y);
						xloc += msize.x;
					}
				}
				if (toolBarManager != null)
					toolBarManager.getControl().setVisible(
							!toolBarManager.isEmpty());
				if (tbsize != null && getToolBarAlignment() == SWT.TOP) {
					ToolBar tbar = toolBarManager.getControl();
					tbar.setBounds(x + width - 1 - tbsize.x - HMARGIN, yloc
							+ row1Height - 1 - tbsize.y, tbsize.x, tbsize.y);
				}
				// second row
				xloc = HMARGIN;
				yloc += row1Height + VSPACING;
				int tw = 0;

				if (tbsize != null && getToolBarAlignment() == SWT.BOTTOM) {
					ToolBar tbar = toolBarManager.getControl();
					if(tbsize!=null){
						tbar.setBounds(x + width - 1 - tbsize.x - HMARGIN, yloc,
								tbsize.x, tbsize.y);
						tw = tbsize.x + SPACING;
					}
				}
				if (headClient != null && clsize!=null) {
					int carea = width - HMARGIN * 2 - tw;
					headClient.setBounds(xloc, yloc, carea, clsize.y);
				}
			}
			return size;
		}
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
	 * Checks for tool bar.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean hasToolBar() {
		return toolBarManager != null && !toolBarManager.isEmpty();
	}

	/**
	 * Checks for message region.
	 *
	 * @author mqfdy
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	private boolean hasMessageRegion() {
		return messageRegion != null && !messageRegion.isEmpty();
	}

	/**
	 * The Class MessageRegion.
	 *
	 * @author mqfdy
	 */
	private class MessageRegion {
		
		/** The message. */
		private String message;
		
		/** The message type. */
		private int messageType;
		
		/** The message label. */
		private CLabel messageLabel;
		
		/** The messages. */
		private IMessage[] messages;
		
		/** The message hyperlink. */
		private Hyperlink messageHyperlink;
		
		/** The listeners. */
		private ListenerList listeners;
		
		/** The fg. */
		private Color fg;
		
		/** The font height. */
		private int fontHeight = -1;
		
		/** The font baseline height. */
		private int fontBaselineHeight = -1;

		/**
		 * Instantiates a new message region.
		 */
		public MessageRegion() {
		}

		/**
		 * Checks if is disposed.
		 *
		 * @author mqfdy
		 * @return true, if is disposed
		 * @Date 2018-09-03 09:00
		 */
		public boolean isDisposed() {
			Control c = getMessageControl();
			return c != null && c.isDisposed();
		}

		/**
		 * Checks if is empty.
		 *
		 * @author mqfdy
		 * @return true, if is empty
		 * @Date 2018-09-03 09:00
		 */
		public boolean isEmpty() {
			Control c = getMessageControl();
			if (c == null)
				return true;
			return !c.getVisible();
		}

		/**
		 * Gets the font height.
		 *
		 * @author mqfdy
		 * @return the font height
		 * @Date 2018-09-03 09:00
		 */
		public int getFontHeight() {
			if (fontHeight == -1) {
				Control c = getMessageControl();
				if (c == null)
					return 0;
				GC gc = new GC(c.getDisplay());
				gc.setFont(c.getFont());
				fontHeight = gc.getFontMetrics().getHeight();
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
		public int getFontBaselineHeight() {
			if (fontBaselineHeight == -1) {
				Control c = getMessageControl();
				if (c == null)
					return 0;
				GC gc = new GC(c.getDisplay());
				gc.setFont(c.getFont());
				FontMetrics fm = gc.getFontMetrics();
				fontBaselineHeight = fm.getHeight() - fm.getDescent();
				gc.dispose();
			}
			return fontBaselineHeight;
		}

		/**
		 * Show message.
		 *
		 * @author mqfdy
		 * @param newMessage
		 *            the new message
		 * @param newType
		 *            the new type
		 * @param messages
		 *            the messages
		 * @Date 2018-09-03 09:00
		 */
		public void showMessage(String newMessage, int newType,
				IMessage[] messages) {
			Control oldControl = getMessageControl();
			int oldType = messageType;
			this.message = newMessage;
			this.messageType = newType;
			this.messages = messages;
			if (newMessage == null) {
				// clearing of the message
				if (oldControl != null && oldControl.getVisible())
					oldControl.setVisible(false);
				return;
			}
			ensureControlExists();
			if (needHyperlink()) {
				messageHyperlink.setText(newMessage);
				messageHyperlink.setHref(messages);
			} else {
				messageLabel.setText(newMessage);
			}
			if (oldType != newType)
				updateForeground();
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
			Control control = getMessageControl();
			if (control != null)
				control.setToolTipText(toolTip);
		}

		/**
		 * Gets the message.
		 *
		 * @author mqfdy
		 * @return the message
		 * @Date 2018-09-03 09:00
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * Gets the message type.
		 *
		 * @author mqfdy
		 * @return the message type
		 * @Date 2018-09-03 09:00
		 */
		public int getMessageType() {
			return messageType;
		}

		/**
		 * Gets the children messages.
		 *
		 * @author mqfdy
		 * @return the children messages
		 * @Date 2018-09-03 09:00
		 */
		public IMessage[] getChildrenMessages() {
			return messages;
		}

		/**
		 * Gets the message control.
		 *
		 * @author mqfdy
		 * @return the message control
		 * @Date 2018-09-03 09:00
		 */
		public Control getMessageControl() {
			if (needHyperlink() && messageHyperlink != null)
				return messageHyperlink;
			return messageLabel;
		}

		/**
		 * Gets the message image.
		 *
		 * @author mqfdy
		 * @return the message image
		 * @Date 2018-09-03 09:00
		 */
		public Image getMessageImage() {
			switch (messageType) {
			case IMessageProvider.INFORMATION:
				return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_INFO);
			case IMessageProvider.WARNING:
				return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_WARNING);
			case IMessageProvider.ERROR:
				return JFaceResources.getImage(Dialog.DLG_IMG_MESSAGE_ERROR);
			default:
				return null;
			}
		}

		/**
		 * Adds the message hyperlink listener.
		 *
		 * @author mqfdy
		 * @param listener
		 *            the listener
		 * @Date 2018-09-03 09:00
		 */
		public void addMessageHyperlinkListener(IHyperlinkListener listener) {
			if (listeners == null)
				listeners = new ListenerList();
			listeners.add(listener);
			ensureControlExists();
			if (messageHyperlink != null)
				messageHyperlink.addHyperlinkListener(listener);
			if (listeners.size() == 1)
				updateForeground();
		}

		/**
		 * Removes the message hyperlink listener.
		 *
		 * @author mqfdy
		 * @param listener
		 *            the listener
		 * @Date 2018-09-03 09:00
		 */
		private void removeMessageHyperlinkListener(IHyperlinkListener listener) {
			if (listeners != null) {
				listeners.remove(listener);
				if (messageHyperlink != null)
					messageHyperlink.removeHyperlinkListener(listener);
				if (listeners.isEmpty())
					listeners = null;
				ensureControlExists();
				if (listeners == null && !isDisposed())
					updateForeground();
			}
		}

		/**
		 * Ensure control exists.
		 *
		 * @author mqfdy
		 * @Date 2018-09-03 09:00
		 */
		private void ensureControlExists() {
			if (needHyperlink()) {
				if (messageLabel != null)
					messageLabel.setVisible(false);
				if (messageHyperlink == null) {
					messageHyperlink = new Hyperlink(FormHeading.this, SWT.NULL);
					messageHyperlink.setUnderlined(true);
					messageHyperlink.setText(message);
					messageHyperlink.setHref(messages);
					Object[] llist = listeners.getListeners();
					for (int i = 0; i < llist.length; i++)
						messageHyperlink
								.addHyperlinkListener((IHyperlinkListener) llist[i]);
					if (messageToolTipManager != null)
						messageToolTipManager.createToolTip(messageHyperlink, false);
				} else if (!messageHyperlink.getVisible()) {
					messageHyperlink.setText(message);
					messageHyperlink.setHref(messages);
					messageHyperlink.setVisible(true);
				}
			} else {
				// need a label
				if (messageHyperlink != null)
					messageHyperlink.setVisible(false);
				if (messageLabel == null) {
					messageLabel = new CLabel(FormHeading.this, SWT.NULL);
					messageLabel.setText(message);
					if (messageToolTipManager != null)
						messageToolTipManager.createToolTip(messageLabel, false);
				} else if (!messageLabel.getVisible()) {
					messageLabel.setText(message);
					messageLabel.setVisible(true);
				}
			}
			layout(true);
		}

		/**
		 * Need hyperlink.
		 *
		 * @author mqfdy
		 * @return true, if successful
		 * @Date 2018-09-03 09:00
		 */
		private boolean needHyperlink() {
			return messageType > 0 && listeners != null;
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
			if (messageHyperlink != null)
				messageHyperlink.setBackground(bg);
			if (messageLabel != null)
				messageLabel.setBackground(bg);
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
			this.fg = fg;
		}

		/**
		 * Update foreground.
		 *
		 * @author mqfdy
		 * @Date 2018-09-03 09:00
		 */
		private void updateForeground() {
			Color theFg;

			switch (messageType) {
			case IMessageProvider.ERROR:
				theFg = getDisplay().getSystemColor(SWT.COLOR_RED);
				break;
			case IMessageProvider.WARNING:
				theFg = getDisplay().getSystemColor(SWT.COLOR_DARK_YELLOW);
				break;
			default:
				theFg = fg;
			}
			getMessageControl().setForeground(theFg);
		}
	}

	/**
	 * Creates the form content control as a child of the provided parent.
	 *
	 * @param parent
	 *            the parent widget
	 * @param style
	 *            the style
	 */
	public FormHeading(Composite parent, int style) {
		super(parent, style);
		setBackgroundMode(SWT.INHERIT_DEFAULT);
		addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event e) {
				onPaint(e.gc);
			}
		});
		addListener(SWT.Dispose, new Listener() {
			public void handleEvent(Event e) {
				if (gradientImage != null) {
					FormImages.getInstance().markFinished(gradientImage, getDisplay());
					gradientImage = null;
				}
			}
		});
		addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event e) {
				if (gradientInfo != null
						|| (backgroundImage != null && !isBackgroundImageTiled()))
					updateGradientImage();
			}
		});
		addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				updateTitleRegionHoverState(e);
			}
		});
		addMouseTrackListener(new MouseTrackListener() {
			public void mouseEnter(MouseEvent e) {
				updateTitleRegionHoverState(e);
			}

			public void mouseExit(MouseEvent e) {
				titleRegion.setHoverState(TitleRegion.STATE_NORMAL);
			}

			public void mouseHover(MouseEvent e) {
			}
		});
		super.setLayout(new FormHeadingLayout());
		titleRegion = new TitleRegion(this);
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
		return ((FormHeadingLayout) getLayout()).computeSize(this, wHint,
				hHint, changed);
	}

	/**
	 * Prevents from changing the custom control layout.
	 *
	 * @author mqfdy
	 * @param layout
	 *            the new layout
	 * @Date 2018-09-03 09:00
	 */
	public final void setLayout(Layout layout) {
	}

	/**
	 * Returns the title text that will be rendered at the top of the form.
	 *
	 * @author mqfdy
	 * @return the title text
	 * @Date 2018-09-03 09:00
	 */
	public String getText() {
		return titleRegion.getText();
	}

	/**
	 * Returns the title image that will be rendered to the left of the title.
	 * 
	 * @return the title image
	 * @since 3.2
	 */
	public Image getImage() {
		return titleRegion.getImage();
	}

	/**
	 * Sets the background color of the header.
	 *
	 * @author mqfdy
	 * @param bg
	 *            the new background
	 * @Date 2018-09-03 09:00
	 */
	public void setBackground(Color bg) {
		super.setBackground(bg);
		internalSetBackground(bg);
	}

	/**
	 * Internal set background.
	 *
	 * @author mqfdy
	 * @param bg
	 *            the bg
	 * @Date 2018-09-03 09:00
	 */
	private void internalSetBackground(Color bg) {
		titleRegion.setBackground(bg);
		if (messageRegion != null)
			messageRegion.setBackground(bg);
		if (toolBarManager != null)
			toolBarManager.getControl().setBackground(bg);
		putColor(COLOR_BASE_BG, bg);
	}

	/**
	 * Sets the foreground color of the header.
	 *
	 * @author mqfdy
	 * @param fg
	 *            the new foreground
	 * @Date 2018-09-03 09:00
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		titleRegion.setForeground(fg);
		if (messageRegion != null)
			messageRegion.setForeground(fg);
	}

	/**
	 * Sets the text to be rendered at the top of the form above the body as a
	 * title.
	 *
	 * @author mqfdy
	 * @param text
	 *            the title text
	 * @Date 2018-09-03 09:00
	 */
	public void setText(String text) {
		titleRegion.setText(text);
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
		titleRegion.setFont(font);
	}

	/**
	 * Sets the image to be rendered to the left of the title.
	 * 
	 * @param image
	 *            the title image or <code>null</code> to show no image.
	 * @since 3.2
	 */
	public void setImage(Image image) {
		titleRegion.setImage(image);
		if (messageRegion != null)
			titleRegion.updateImage(messageRegion.getMessageImage(), true);
		else
			titleRegion.updateImage(null, true);
	}

	/**
	 * Sets the text background.
	 *
	 * @author mqfdy
	 * @param gradientColors
	 *            the gradient colors
	 * @param percents
	 *            the percents
	 * @param vertical
	 *            the vertical
	 * @Date 2018-09-03 09:00
	 */
	public void setTextBackground(Color[] gradientColors, int[] percents,
			boolean vertical) {
		if (gradientColors != null) {
			gradientInfo = new GradientInfo();
			gradientInfo.gradientColors = gradientColors;
			gradientInfo.percents = percents;
			gradientInfo.vertical = vertical;
			setBackground(null);
			updateGradientImage();
		} else {
			// reset
			gradientInfo = null;
			if (gradientImage != null) {
				FormImages.getInstance().markFinished(gradientImage, getDisplay());
				gradientImage = null;
				setBackgroundImage(null);
			}
		}
	}

	/**
	 * Sets the heading background image.
	 *
	 * @author mqfdy
	 * @param image
	 *            the new heading background image
	 * @Date 2018-09-03 09:00
	 */
	public void setHeadingBackgroundImage(Image image) {
		this.backgroundImage = image;
		if (image != null)
			setBackground(null);
		if (isBackgroundImageTiled()) {
			setBackgroundImage(image);
		} else
			updateGradientImage();
	}

	/**
	 * Gets the heading background image.
	 *
	 * @author mqfdy
	 * @return the heading background image
	 * @Date 2018-09-03 09:00
	 */
	public Image getHeadingBackgroundImage() {
		return backgroundImage;
	}

	/**
	 * Sets the background image tiled.
	 *
	 * @author mqfdy
	 * @param tiled
	 *            the new background image tiled
	 * @Date 2018-09-03 09:00
	 */
	public void setBackgroundImageTiled(boolean tiled) {
		if (tiled)
			flags |= BACKGROUND_IMAGE_TILED;
		else
			flags &= ~BACKGROUND_IMAGE_TILED;
		setHeadingBackgroundImage(this.backgroundImage);
	}

	/**
	 * Checks if is background image tiled.
	 *
	 * @author mqfdy
	 * @return true, if is background image tiled
	 * @Date 2018-09-03 09:00
	 */
	public boolean isBackgroundImageTiled() {
		return (flags & BACKGROUND_IMAGE_TILED) != 0;
	}

	/**
	 * Sets the background image.
	 *
	 * @author mqfdy
	 * @param image
	 *            the new background image
	 * @Date 2018-09-03 09:00
	 */
	public void setBackgroundImage(Image image) {
		super.setBackgroundImage(image);
		if (image != null) {
			internalSetBackground(null);
		}
	}

	/**
	 * Returns the tool bar manager that is used to manage tool items in the
	 * form's title area.
	 *
	 * @author mqfdy
	 * @return form tool bar manager
	 * @Date 2018-09-03 09:00
	 */
	public IToolBarManager getToolBarManager() {
		if (toolBarManager == null) {
			toolBarManager = new ToolBarManager(SWT.FLAT);
			ToolBar toolbar = toolBarManager.createControl(this);
			toolbar.setBackground(getBackground());
			toolbar.setForeground(getForeground());
			toolbar.setCursor(FormsResources.getHandCursor());
			addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					if (toolBarManager != null) {
						toolBarManager.dispose();
						toolBarManager = null;
					}
				}
			});
		}
		return toolBarManager;
	}

	/**
	 * Returns the menu manager that is used to manage tool items in the form's
	 * title area.
	 * 
	 * @return form drop-down menu manager
	 * @since 3.3
	 */
	public IMenuManager getMenuManager() {
		return titleRegion.getMenuManager();
	}

	/**
	 * Updates the local tool bar manager if used. Does nothing if local tool
	 * bar manager has not been created yet.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	public void updateToolBar() {
		if (toolBarManager != null)
			toolBarManager.update(false);
	}

	/**
	 * On paint.
	 *
	 * @author mqfdy
	 * @param gc
	 *            the gc
	 * @Date 2018-09-03 09:00
	 */
	private void onPaint(GC gc) {
		if (!isSeparatorVisible() && getBackgroundImage() == null)
			return;
		Rectangle carea = getClientArea();
		Image buffer = new Image(getDisplay(), carea.width, carea.height);
		buffer.setBackground(getBackground());
		GC igc = new GC(buffer);
		igc.setBackground(getBackground());
		igc.fillRectangle(0, 0, carea.width, carea.height);
		if (getBackgroundImage() != null) {
			if (gradientInfo != null)
				drawBackground(igc, carea.x, carea.y, carea.width, carea.height);
			else {
				Image bgImage = getBackgroundImage();
				Rectangle ibounds = bgImage.getBounds();
				drawBackground(igc, carea.x, carea.y, ibounds.width,
						ibounds.height);
			}
		}

		if (isSeparatorVisible()) {
			// bg separator
			if (hasColor(IFormColors.H_BOTTOM_KEYLINE1))
				igc.setForeground(getColor(IFormColors.H_BOTTOM_KEYLINE1));
			else
				igc.setForeground(getBackground());
			igc.drawLine(carea.x, carea.height - 2, carea.x + carea.width - 1,
					carea.height - 2);
			if (hasColor(IFormColors.H_BOTTOM_KEYLINE2))
				igc.setForeground(getColor(IFormColors.H_BOTTOM_KEYLINE2));
			else
				igc.setForeground(getForeground());
			igc.drawLine(carea.x, carea.height - 1, carea.x + carea.width - 1,
					carea.height - 1);
		}
		igc.dispose();
		gc.drawImage(buffer, carea.x, carea.y);
		buffer.dispose();
	}

	/**
	 * Update title region hover state.
	 *
	 * @author mqfdy
	 * @param e
	 *            the e
	 * @Date 2018-09-03 09:00
	 */
	private void updateTitleRegionHoverState(MouseEvent e) {
		Rectangle titleRect = titleRegion.getBounds();
		titleRect.width += titleRect.x + 15;
		titleRect.height += titleRect.y + 15;
		titleRect.x = 0;
		titleRect.y = 0;
		if (titleRect.contains(e.x, e.y))
			titleRegion.setHoverState(TitleRegion.STATE_HOVER_LIGHT);
		else
			titleRegion.setHoverState(TitleRegion.STATE_NORMAL);
	}

	/**
	 * Update gradient image.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void updateGradientImage() {
		Rectangle rect = getBounds();
		if (gradientImage != null) {
			FormImages.getInstance().markFinished(gradientImage, getDisplay());
			gradientImage = null;
		}
		if (gradientInfo != null) {
			gradientImage = FormImages.getInstance().getGradient(gradientInfo.gradientColors, gradientInfo.percents,
					gradientInfo.vertical ? rect.height : rect.width, gradientInfo.vertical, getColor(COLOR_BASE_BG), getDisplay());
		} else if (backgroundImage != null && !isBackgroundImageTiled()) {
			gradientImage = new Image(getDisplay(), Math.max(rect.width, 1),
					Math.max(rect.height, 1));
			gradientImage.setBackground(getBackground());
			GC gc = new GC(gradientImage);
			gc.drawImage(backgroundImage, 0, 0);
			gc.dispose();
		}
		setBackgroundImage(gradientImage);
	}

	/**
	 * Checks if is separator visible.
	 *
	 * @author mqfdy
	 * @return true, if is separator visible
	 * @Date 2018-09-03 09:00
	 */
	public boolean isSeparatorVisible() {
		return (flags & SEPARATOR) != 0;
	}

	/**
	 * Sets the separator visible.
	 *
	 * @author mqfdy
	 * @param addSeparator
	 *            the new separator visible
	 * @Date 2018-09-03 09:00
	 */
	public void setSeparatorVisible(boolean addSeparator) {
		if (addSeparator)
			flags |= SEPARATOR;
		else
			flags &= ~SEPARATOR;
	}

	/**
	 * Sets the tool bar alignment.
	 *
	 * @author mqfdy
	 * @param alignment
	 *            the new tool bar alignment
	 * @Date 2018-09-03 09:00
	 */
	public void setToolBarAlignment(int alignment) {
		if (alignment == SWT.BOTTOM)
			flags |= BOTTOM_TOOLBAR;
		else
			flags &= ~BOTTOM_TOOLBAR;
	}

	/**
	 * Gets the tool bar alignment.
	 *
	 * @author mqfdy
	 * @return the tool bar alignment
	 * @Date 2018-09-03 09:00
	 */
	public int getToolBarAlignment() {
		return (flags & BOTTOM_TOOLBAR) != 0 ? SWT.BOTTOM : SWT.TOP;
	}

	/**
	 * Adds the message hyperlink listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void addMessageHyperlinkListener(IHyperlinkListener listener) {
		ensureMessageRegionExists();
		messageRegion.addMessageHyperlinkListener(listener);
	}

	/**
	 * Removes the message hyperlink listener.
	 *
	 * @author mqfdy
	 * @param listener
	 *            the listener
	 * @Date 2018-09-03 09:00
	 */
	public void removeMessageHyperlinkListener(IHyperlinkListener listener) {
		if (messageRegion != null)
			messageRegion.removeMessageHyperlinkListener(listener);
	}

	/**
	 * Gets the message.
	 *
	 * @author mqfdy
	 * @return the message
	 * @Date 2018-09-03 09:00
	 */
	public String getMessage() {
		return messageRegion != null ? messageRegion.getMessage() : null;
	}

	/**
	 * Gets the message type.
	 *
	 * @author mqfdy
	 * @return the message type
	 * @Date 2018-09-03 09:00
	 */
	public int getMessageType() {
		return messageRegion != null ? messageRegion.getMessageType() : 0;
	}

	/**
	 * Gets the children messages.
	 *
	 * @author mqfdy
	 * @return the children messages
	 * @Date 2018-09-03 09:00
	 */
	public IMessage[] getChildrenMessages() {
		return messageRegion != null ? messageRegion.getChildrenMessages()
				: NULL_MESSAGE_ARRAY;
	}

	/**
	 * Ensure message region exists.
	 *
	 * @author mqfdy
	 * @Date 2018-09-03 09:00
	 */
	private void ensureMessageRegionExists() {
		// ensure message region exists
		if (messageRegion == null)
			messageRegion = new MessageRegion();
	}

	/**
	 * Show message.
	 *
	 * @author mqfdy
	 * @param newMessage
	 *            the new message
	 * @param type
	 *            the type
	 * @param messages
	 *            the messages
	 * @Date 2018-09-03 09:00
	 */
	public void showMessage(String newMessage, int type, IMessage[] messages) {
		if (messageRegion == null) {
			// check the trivial case
			if (newMessage == null)
				return;
		} else if (messageRegion.isDisposed())
			return;
		ensureMessageRegionExists();
		messageRegion.showMessage(newMessage, type, messages);
		titleRegion.updateImage(messageRegion.getMessageImage(), false);
		if (messageToolTipManager != null)
			messageToolTipManager.update();
		layout();
		redraw();
	}

	/**
	 * Tests if the form is in the 'busy' state.
	 *
	 * @author mqfdy
	 * @return <code>true</code> if busy, <code>false</code> otherwise.
	 * @Date 2018-09-03 09:00
	 */

	public boolean isBusy() {
		return titleRegion.isBusy();
	}

	/**
	 * Sets the form's busy state. Busy form will display 'busy' animation in
	 * the area of the title image.
	 *
	 * @author mqfdy
	 * @param busy
	 *            the form's busy state
	 * @Date 2018-09-03 09:00
	 */

	public void setBusy(boolean busy) {
		if (titleRegion.setBusy(busy))
			layout();
	}

	/**
	 * Gets the head client.
	 *
	 * @author mqfdy
	 * @return the head client
	 * @Date 2018-09-03 09:00
	 */
	public Control getHeadClient() {
		return headClient;
	}

	/**
	 * Sets the head client.
	 *
	 * @author mqfdy
	 * @param headClient
	 *            the new head client
	 * @Date 2018-09-03 09:00
	 */
	public void setHeadClient(Control headClient) {
		if (headClient != null)
			Assert.isTrue(headClient.getParent() == this);
		this.headClient = headClient;
		layout();
	}

	/**
	 * Put color.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @param color
	 *            the color
	 * @Date 2018-09-03 09:00
	 */
	public void putColor(String key, Color color) {
		if (color == null)
			colors.remove(key);
		else
			colors.put(key, color);
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
	public Color getColor(String key) {
		return (Color) colors.get(key);
	}

	/**
	 * Checks for color.
	 *
	 * @author mqfdy
	 * @param key
	 *            the key
	 * @return true, if successful
	 * @Date 2018-09-03 09:00
	 */
	public boolean hasColor(String key) {
		return colors.containsKey(key);
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
		titleRegion.addDragSupport(operations, transferTypes, listener);
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
		titleRegion.addDropSupport(operations, transferTypes, listener);
	}

	/**
	 * Gets the message tool tip manager.
	 *
	 * @author mqfdy
	 * @return the message tool tip manager
	 * @Date 2018-09-03 09:00
	 */
	public IMessageToolTipManager getMessageToolTipManager() {
		return messageToolTipManager;
	}

	/**
	 * Sets the message tool tip manager.
	 *
	 * @author mqfdy
	 * @param messageToolTipManager
	 *            the new message tool tip manager
	 * @Date 2018-09-03 09:00
	 */
	public void setMessageToolTipManager(
			IMessageToolTipManager messageToolTipManager) {
		this.messageToolTipManager = messageToolTipManager;
	}
}

