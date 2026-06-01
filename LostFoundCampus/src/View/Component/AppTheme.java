package View.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public final class AppTheme {

    // === COLOR PALETTE === (Indigo/Teal harmony)
    public static final Color BACKGROUND       = new Color(240, 244, 248);
    public static final Color SURFACE          = Color.WHITE;
    public static final Color SIDEBAR          = new Color(22, 33, 62);       // deep indigo
    public static final Color SIDEBAR_HOVER    = new Color(41, 55, 99);
    public static final Color SIDEBAR_ACCENT   = new Color(99, 179, 237);     // sky teal accent

    public static final Color PRIMARY          = new Color(67, 97, 238);      // vivid indigo
    public static final Color PRIMARY_HOVER    = new Color(49, 75, 213);
    public static final Color PRIMARY_LIGHT    = new Color(224, 229, 253);

    public static final Color ACCENT           = new Color(72, 199, 186);     // teal accent
    public static final Color ACCENT_LIGHT     = new Color(209, 247, 244);

    public static final Color DANGER           = new Color(239, 68, 68);
    public static final Color DANGER_LIGHT     = new Color(254, 226, 226);
    public static final Color SUCCESS          = new Color(16, 185, 129);
    public static final Color SUCCESS_LIGHT    = new Color(209, 250, 229);
    public static final Color WARNING          = new Color(245, 158, 11);
    public static final Color WARNING_LIGHT    = new Color(254, 243, 199);

    public static final Color TEXT_PRIMARY     = new Color(17, 24, 39);
    public static final Color TEXT_SECONDARY   = new Color(75, 85, 99);
    public static final Color TEXT_MUTED       = new Color(156, 163, 175);
    public static final Color TEXT_ON_PRIMARY  = Color.WHITE;
    public static final Color TEXT_ON_LIGHT    = new Color(17, 24, 39);

    public static final Color BORDER           = new Color(229, 231, 235);
    public static final Color BORDER_FOCUS     = new Color(67, 97, 238);

    public static final Color CARD_SHADOW      = new Color(0, 0, 0, 20);
    public static final Color TABLE_STRIPE     = new Color(249, 250, 251);
    public static final Color TABLE_HEADER     = new Color(239, 242, 255);

    // Badge / status colors
    public static final Color BADGE_KLAIM_BG   = new Color(220, 252, 231);
    public static final Color BADGE_KLAIM_FG   = new Color(21, 128, 61);
    public static final Color BADGE_PENDING_BG = new Color(254, 249, 195);
    public static final Color BADGE_PENDING_FG = new Color(133, 77, 14);

    // === TYPOGRAPHY ===
    public static final Font TITLE_FONT        = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font SECTION_TITLE_FONT= new Font("Segoe UI", Font.BOLD, 18);
    public static final Font CARD_TITLE_FONT   = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font SUBTITLE_FONT     = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font LABEL_FONT        = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font BODY_FONT         = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font BUTTON_FONT       = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font SIDEBAR_FONT      = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font SMALL_FONT        = new Font("Segoe UI", Font.PLAIN, 11);

    // === WINDOW SIZES ===
    public static final Dimension WINDOW_AUTH           = new Dimension(420, 340);
    public static final Dimension WINDOW_AUTH_REGISTER  = new Dimension(420, 420);
    public static final Dimension WINDOW_HOME           = new Dimension(520, 460);
    public static final Dimension WINDOW_FORM           = new Dimension(560, 620);
    public static final Dimension WINDOW_TABLE          = new Dimension(900, 580);
    public static final Dimension WINDOW_DASHBOARD      = new Dimension(1000, 660);
    public static final Dimension WINDOW_COMPACT        = new Dimension(420, 340);

    // === DIALOG SIZES ===
    public static final Dimension DIALOG_FORM           = new Dimension(520, 560);
    public static final Dimension DIALOG_TABLE          = new Dimension(860, 520);
    public static final Dimension DIALOG_CLAIM          = new Dimension(480, 360);

    private AppTheme() {}
}
