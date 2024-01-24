package com.castis.pvs.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String RUNTIME_DISPLAY_DEFAULT = "00:00:00";

	public static class COMMONCODE {

		public static final String NOT_ADDRESSED = "N/A";
		public static final String NO_INFORMATION = "No information";
		public static final String ROOT_GROUP_CODE = "000000";
		public static final String GROUP_CODE_LANGUAGE = "LAN001";
		public static final String GROUP_CODE_MENU_EXTID = "EXTID";
		public static final String GROUP_CODE_MENU_LINK_TYPE = "LTYPE";
		public static final String GROUP_CODE_MENU_LINK_INFO_BANNER_POSITION = "BNRPOS";
		public static final String GROUP_CODE_MENU_LINK_INFO_BANNER_POSITION_PRESETCODE = "BNRPRE";
		public static final String PRESET_VOD_HD = "vod_hd";
		public static final String PRESET_VOD_UHD = "vod_uhd";
		public static final String GROUP_CODE_MENU_LINK_INFO_THIRD_PARTY = "3RDAPP";
		public static final String GROUP_CODE_ENCRYPTION_TYPE = "ENC001";
		public static final String GROUP_CODE_RATIO_CONFIG = "RSZ";
		public static final String GROUP_CODE_DEVICE = "DEVICE";
		public static final String GROUP_CODE_PREVIEW_COVER_IMG_RATIO_CONFIG = "PRERSZ";
		public static final String OFFER_CODE_RATIO_CONFIG = "OFRSZ";
		public static final String BANNER_POSITION_TYPE = "bannerPosition";
		public static final String THIRD_PARTY_TYPE = "3rdPartyApp";
		public static final String PROMO_POSITION_TYPE = "promoPosition";
		public static final String THUMBNAIL_OUTPUT_WIDTH = "OWIDTH";
		public static final String MENU_TYPE = "menu";
		public static final String ASSET_TYPE = "asset";
		public static final String CG_RATING = "CG0003";
		public static final String CG_GENRE = "CG0004";
		public static final String BADGE = "BADGE";
		public static final Short Y = 1;
		public static final Short N = 0;
		public static final String NO_IMAGE_BASE64 = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2ODApLCBxdWFsaXR5ID0gOTkK/9sAQwABAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQECAgEBAgEBAQICAgICAgICAgECAgICAgICAgIC/9sAQwEBAQEBAQEBAQEBAgEBAQICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIC/8AAEQgDAAQAAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8AKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/9k=";
		public static final String GROUP_CODE_MESSAGE_ID = "MESSGROUP";
		public static final short ADD = 1;
		public static final short EDIT = 2;
		public static final short DELETE = 3;
		
		public static final String NO_RENTAL_PERIOD = "";
		public static final String NO_WATCH_PERIOD = "";
		public static final String WATCH_PERIOD_UNLIMITED = "9999";
		public static final String RENTAL_PERIOD_UNLIMITED = "9999";

		private COMMONCODE() {
		}
	}

	public static class VIDEO_RESOLUTION {
		public static final String SD_VIDEO = "SD";
		public static final String HD_VIDEO = "HD";
		public static final String FULL_HD_VIDEO = "FHD";
		public static final String QUAD_HD_VIDEO = "QHD";
		public static final String UHD_VIDEO = "UHD";
	}

//	public static class RESULTCODE {
//
//		public static final int SUCCESS = 200;
//		public static final int CREATED = 201;
//		public static final int BAD_REQUEST = 400;
//		public static final int NOT_FOUND_SOURCE = 404;
//		public static final int FAIL = 500;
//
//		private RESULTCODE() {
//		}
//	}

	public static class RETURN_CODE {
		public static final String SUCCESS = "S";
		public static final String FAIL = "F";
	}

	public static class TRANSCODER_RESPONSE_STATUS {
		public static final int DONE = 0;
		public static final int WAITING = 1;
		public static final int RUNNING = 2;
		public static final int STOP = 3;
		public static final int ERROR = -1;
	}

	public static class TRANSMISSION_SECTION {
		public static final String REQUEST = "REQ";
		public static final String RESPONSE = "RES";
	}

	public static class STR_ASSET_TYPE {
		public static final String CONTENT_GROUP_ASSET_TYPE = "contentGroupAsset";
		public static final String TITLE_ASSET_TYPE = "titleAsset";
		public static final String PREVIEW_ASSET_TYPE = "previewAsset";
		public static final String MOVIE_ASSET_TYPE = "movieAsset";
		public static final String POSTER_ASSET_TYPE = "posterAsset";
		public static final String OFFER_ASSET_TYPE = "offerAsset";
		public static final String SERIES_ASSET_TYPE = "seriesAsset";
		public static final String MASTER_SERIES_ASSET_TYPE = "masterSeriesAsset";
		public static final String BANNER_CLIP_ASSET_TYPE = "bannerClipAsset";
		public static final String BANNER_IMAGE_ASSET_TYPE = "bannerImageAsset";
	}

	public static class STR_DISTRIBUTE_STATUS {
		public static final String READY = "Ready";
		public static final String UPLOADING = "Uploading";
		public static final String SUCCESS = "Success";
		public static final String ERROR = "Error";
	}

	public static class DEPLOY_DESCRIPTION {
		public static final String PREPROCESSING_FAILED = "Preprocessing flow was failed";
		public static final String DISTRIBUTING_FAILED = "Distributing flow was failed";
	}

	// Not used currently
	public enum UserState {
		DISABLED, IN_USE, UNKNOWN;

		public static Map<Integer, String> getEnumMap() {
			Map<Integer, String> enumMap = new HashMap<>();
			enumMap.put(DISABLED.ordinal(), DISABLED.name());
			enumMap.put(IN_USE.ordinal(), IN_USE.name());
			enumMap.put(UNKNOWN.ordinal(), UNKNOWN.name());
			return enumMap;
		}
	}

	public enum RequestMethodType {
		ADD((short) 1), EDIT((short) 2), DELETE((short) 3);
		private short code;

		RequestMethodType(short code) {
			this.code = code;
		}

		public short getCode() {
			return this.code;
		}

		public static RequestMethodType getRequestMethodType(Short code) {
			switch (code) {
				case 1:
					return ADD;
				case 2:
					return EDIT;
				case 3:
					return DELETE;
				default:
					return null;
			}
		}

		public static String getMethodNameByCode(short code) {
			switch (code) {
				case 1:
					return ADD.name();
				case 2:
					return EDIT.name();
				case 3:
					return DELETE.name();
				default:
					return EDIT.name();
			}
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> enumMap = new HashMap<>();
			enumMap.put(ADD.code, ADD.name());
			enumMap.put(EDIT.code, EDIT.name());
			enumMap.put(DELETE.code, DELETE.name());
			return enumMap;
		}
	}

	public enum UserType {
		SYSADMIN("ROLE_SYSADMIN", "SYSADMIN"), SO("ROLE_SO", "SO"), CP("ROLE_CP", "CP"), AD_CP("ROLE_AD_CP", "AD_CP"),
		QC("ROLE_QC", "QC"), UNKNOWN("Unknown", "Unknown");

		private String value;
		private String role;

		UserType(String value, String role) {
			this.value = value;
			this.role = role;
		}

		public String getValue() {
			return this.value;
		}

		public String getRole() {
			return this.role;
		}

		public static Map<String, String> getEnumMap() {
			Map<String, String> enumMap = new HashMap<>();

			enumMap.put(SYSADMIN.name(), SYSADMIN.value);
			enumMap.put(SO.name(), SO.value);
			enumMap.put(CP.name(), CP.value);
			enumMap.put(AD_CP.name(), AD_CP.value);
			enumMap.put(QC.name(), QC.value);
			enumMap.put(UNKNOWN.name(), UNKNOWN.value);
			return enumMap;
		}

		public static Map<Integer, String> getEnumMapByOrdinal() {
			Map<Integer, String> enumMap = new HashMap<>();

			enumMap.put(SYSADMIN.ordinal(), SYSADMIN.value);
			enumMap.put(SO.ordinal(), SO.value);
			enumMap.put(CP.ordinal(), CP.value);
			enumMap.put(AD_CP.ordinal(), AD_CP.value);
			enumMap.put(QC.ordinal(), QC.value);
			enumMap.put(UNKNOWN.ordinal(), UNKNOWN.value);
			return enumMap;
		}
	}

	public enum AssetTypeFilename {
		MOVIE("movie"), PREVIEW("preview"), BANNER_CLIP("bannerClip"), BANNER_IMAGE("bannerImage"), AD_CLIP("adClip"),
		THUMBNAIL("thumbnail"), POSTER("poster"), CONTENT_GROUP("contentGroupAsset"), SERIES("seriesAsset"),
		SUBTITLE("subtitle");

		private String fileName;

		AssetTypeFilename(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return this.fileName;
		}
	}

	public enum AssetType {
		CONTENT_PROVIDER((short) 1), CONTENT_GROUP_ASSET((short) 11), TITLE_ASSET((short) 21), MOVIE_ASSET((short) 31), PREVIEW_ASSET((short) 32),
		AD_CLIP_ASSET((short) 33), PROMO_CLIP_ASSET((short) 34), AD_MOVIE((short) 35), POSTER_ASSET((short) 41), OFFER_ASSET((short) 51), MENU_ASSET((short) 61),
		MENU_MAP((short) 62), SERIES_ASSET((short) 71), MASTER_SERIES_ASSET((short) 72), BANNER_IMAGE_ASSET((short) 81),
		BANNER_CLIP_ASSET((short) 82), BADGE_ASSET((short) 101), GENRE_CODE((short) 102), BANNER_ASSET((short) 111),
		BANNER_LINEUP((short) 112), MESSAGE_BANNER_ASSET((short) 115), CHANNEL_BANNER_ASSET((short) 116), UNKNOWN((short) 99);
		private short code;

		AssetType(short code) {
			this.code = code;
		}

		public short getCode() {
			return this.code;
		}

		public static AssetType getAssetTypeByCode(short code) {
			switch (code) {
				case (short) 1:
					return CONTENT_PROVIDER;
				case (short) 11:
					return CONTENT_GROUP_ASSET;
				case (short) 21:
					return TITLE_ASSET;
				case (short) 31:
					return MOVIE_ASSET;
				case (short) 32:
					return PREVIEW_ASSET;
				case (short) 33:
					return AD_CLIP_ASSET;
				case (short) 34:
					return PROMO_CLIP_ASSET;
				case (short) 35:
					return AD_MOVIE;
				case (short) 41:
					return POSTER_ASSET;
				case (short) 51:
					return OFFER_ASSET;
				case (short) 61:
					return MENU_ASSET;
				case (short) 62:
					return MENU_MAP;
				case (short) 71:
					return SERIES_ASSET;
				case (short) 72:
					return MASTER_SERIES_ASSET;
				case (short) 81:
					return BANNER_IMAGE_ASSET;
				case (short) 82:
					return BANNER_CLIP_ASSET;
				case (short) 101:
					return BADGE_ASSET;
				case (short) 102:
					return GENRE_CODE;
				case (short) 111:
					return BANNER_ASSET;
				case (short) 112:
					return BANNER_LINEUP;
				case (short) 115:
					return MESSAGE_BANNER_ASSET;
				case (short) 116:
					return CHANNEL_BANNER_ASSET;
				case (short) 99:
				default:
					return UNKNOWN;
			}
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(CONTENT_GROUP_ASSET.code, CONTENT_GROUP_ASSET.name());
			mapAssetType.put(TITLE_ASSET.code, TITLE_ASSET.name());
			mapAssetType.put(MOVIE_ASSET.code, MOVIE_ASSET.name());
			mapAssetType.put(PREVIEW_ASSET.code, PREVIEW_ASSET.name());
			mapAssetType.put(AD_CLIP_ASSET.code, AD_CLIP_ASSET.name());
			mapAssetType.put(PROMO_CLIP_ASSET.code, PROMO_CLIP_ASSET.name());
			mapAssetType.put(AD_MOVIE.code, AD_MOVIE.name());
			mapAssetType.put(POSTER_ASSET.code, POSTER_ASSET.name());
			mapAssetType.put(OFFER_ASSET.code, OFFER_ASSET.name());
			mapAssetType.put(MENU_ASSET.code, MENU_ASSET.name());
			mapAssetType.put(MENU_MAP.code, MENU_MAP.name());
			mapAssetType.put(BANNER_IMAGE_ASSET.code, BANNER_IMAGE_ASSET.name());
			mapAssetType.put(BANNER_CLIP_ASSET.code, BANNER_CLIP_ASSET.name());
			mapAssetType.put(SERIES_ASSET.code, SERIES_ASSET.name());
			mapAssetType.put(MASTER_SERIES_ASSET.code, MASTER_SERIES_ASSET.name());
			mapAssetType.put(BANNER_ASSET.code, BANNER_ASSET.name());
			return mapAssetType;
		}

		public static Map<Short, String> getHistoryEnumMap() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(CONTENT_GROUP_ASSET.code, "Content group");
			mapAssetType.put(OFFER_ASSET.code, "Offer");
			mapAssetType.put(SERIES_ASSET.code, "Series");
			mapAssetType.put(MASTER_SERIES_ASSET.code, "Master series");
			mapAssetType.put(TITLE_ASSET.code, "Title");
			mapAssetType.put(MOVIE_ASSET.code, "Movie");
			mapAssetType.put(PREVIEW_ASSET.code, "Preview");
			mapAssetType.put(POSTER_ASSET.code, "Poster");
			mapAssetType.put(BANNER_ASSET.code, "Banner");
			mapAssetType.put(BANNER_IMAGE_ASSET.code, "Banner Image");
			mapAssetType.put(BANNER_CLIP_ASSET.code, "Banner Clip");
			mapAssetType.put(BANNER_LINEUP.code, "Banner LineUp");
			mapAssetType.put(AD_CLIP_ASSET.code, "AD Clip");
			mapAssetType.put(AD_MOVIE.code, "AD Movie");
			mapAssetType.put(PROMO_CLIP_ASSET.code, "Promo Clip");
			mapAssetType.put(MENU_ASSET.code, "Menu");
			return mapAssetType;
		}

		public static Map<Short, String> getHistorySearchEnumMap() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(CONTENT_GROUP_ASSET.code, "Content group");
			mapAssetType.put(OFFER_ASSET.code, "Offer");
			mapAssetType.put(SERIES_ASSET.code, "Series");
			mapAssetType.put(MASTER_SERIES_ASSET.code, "Master series");
			return mapAssetType;
		}

		public static Map<Short, String> getCatalogueDeploymentHistorySearchEnumMap() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(MENU_MAP.code, "AssetLineUp");
			mapAssetType.put(MENU_ASSET.code, "MenuTree");
			return mapAssetType;
		}

		public static String getStrAssetType(short code) {
			switch (code) {
				case (short) 11:
					return "contentGroupAsset";
				case (short) 21:
					return "titleAsset";
				case (short) 31:
					return "movieAsset";
				case (short) 32:
					return "previewAsset";
				case (short) 33:
					return "adClipAsset";
				case (short) 41:
					return "posterAsset";
				case (short) 51:
					return "offerAsset";
				case (short) 61:
					return "menuAsset";
				case (short) 71:
					return "seriesAsset";
				case (short) 72:
					return "masterSeriesAsset";
				case (short) 81:
					return "bannerImageAsset";
				case (short) 82:
					return "bannerClipAsset";
				case (short) 101:
					return "badgeAsset";
				case (short) 102:
					return "genreCode";
				case (short) 111:
					return "bannerAsset";
				default:
					return null;
			}
		}
	}

	public enum AssetStage {
		REGISTERED((short) 5), REJECTED((short) 25), APPROVED((short) 29), PREPROCESSING((short) 30),
		PREPROCESS_FAILED((short) 35), PREPROCESS_COMPLETED((short) 39), DISTRIBUTING((short) 40),
		DISTRIBUTE_FAILED((short) 45), DISTRIBUTE_COMPLETED((short) 49), READY_FOR_DEPLOYING((short) 53),
		NON_READY_FOR_DEPLOYING((short) 50), DEPLOY_FAILED((short) 55), DEPLOY_COMPLETED((short) 59),
		ADS_SENT((short) 60), ADS_FAILED((short) 65), ADVERTISING((short) 67), ADS_COMPLETED((short) 69),
		DELETED((short) 99), UNKNOWN((short) 999);
		private short code;

		AssetStage(short code) {
			this.code = code;
		}

		public short getCode() {
			return this.code;
		}

		public static AssetStage getAssetStageCodeByCode(short code) {
			switch (code) {
				case (short) 5:
					return REGISTERED;
				case (short) 25:
					return REJECTED;
				case (short) 29:
					return APPROVED;
				case (short) 30:
					return PREPROCESSING;
				case (short) 35:
					return PREPROCESS_FAILED;
				case (short) 39:
					return PREPROCESS_COMPLETED;
				case (short) 40:
					return DISTRIBUTING;
				case (short) 45:
					return DISTRIBUTE_FAILED;
				case (short) 49:
					return DISTRIBUTE_COMPLETED;
				case (short) 50:
					return NON_READY_FOR_DEPLOYING;
				case (short) 53:
					return READY_FOR_DEPLOYING;
				case (short) 55:
					return DEPLOY_FAILED;
				case (short) 59:
					return DEPLOY_COMPLETED;
				case (short) 60:
					return ADS_SENT;
				case (short) 65:
					return ADS_FAILED;
				case (short) 67:
					return ADVERTISING;
				case (short) 69:
					return ADS_COMPLETED;
				case (short) 99:
					return DELETED;
				case (short) 999:
				default:
					return UNKNOWN;
			}
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(REGISTERED.code, REGISTERED.name());
			mapAssetType.put(APPROVED.code, APPROVED.name());
			mapAssetType.put(REJECTED.code, REJECTED.name());
			mapAssetType.put(PREPROCESSING.code, PREPROCESSING.name());
			mapAssetType.put(PREPROCESS_COMPLETED.code, PREPROCESS_COMPLETED.name());
			mapAssetType.put(PREPROCESS_FAILED.code, PREPROCESS_FAILED.name());
			mapAssetType.put(DISTRIBUTING.code, DISTRIBUTING.name());
			mapAssetType.put(DISTRIBUTE_COMPLETED.code, DISTRIBUTE_COMPLETED.name());
			mapAssetType.put(DISTRIBUTE_FAILED.code, DISTRIBUTE_FAILED.name());
			mapAssetType.put(READY_FOR_DEPLOYING.code, READY_FOR_DEPLOYING.name());
			mapAssetType.put(NON_READY_FOR_DEPLOYING.code, NON_READY_FOR_DEPLOYING.name());
			mapAssetType.put(DEPLOY_COMPLETED.code, DEPLOY_COMPLETED.name());
			mapAssetType.put(DEPLOY_FAILED.code, DEPLOY_FAILED.name());
			mapAssetType.put(DELETED.code, DELETED.name());
			mapAssetType.put(ADS_SENT.code, ADS_SENT.name());
			mapAssetType.put(ADS_FAILED.code, ADS_FAILED.name());
			mapAssetType.put(ADVERTISING.code, ADVERTISING.name());
			mapAssetType.put(ADS_COMPLETED.code, ADS_COMPLETED.name());
			mapAssetType.put(DELETED.code, DELETED.name());
			return mapAssetType;
		}

		public static Map<Short, String> getEnumMapNonAds() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(REGISTERED.code, REGISTERED.name());
			mapAssetType.put(APPROVED.code, APPROVED.name());
			mapAssetType.put(REJECTED.code, REJECTED.name());
			mapAssetType.put(PREPROCESSING.code, PREPROCESSING.name());
			mapAssetType.put(PREPROCESS_COMPLETED.code, PREPROCESS_COMPLETED.name());
			mapAssetType.put(PREPROCESS_FAILED.code, PREPROCESS_FAILED.name());
			mapAssetType.put(DISTRIBUTING.code, DISTRIBUTING.name());
			mapAssetType.put(DISTRIBUTE_COMPLETED.code, DISTRIBUTE_COMPLETED.name());
			mapAssetType.put(DISTRIBUTE_FAILED.code, DISTRIBUTE_FAILED.name());
			mapAssetType.put(READY_FOR_DEPLOYING.code, READY_FOR_DEPLOYING.name());
			mapAssetType.put(NON_READY_FOR_DEPLOYING.code, NON_READY_FOR_DEPLOYING.name());
			mapAssetType.put(DEPLOY_COMPLETED.code, DEPLOY_COMPLETED.name());
			mapAssetType.put(DEPLOY_FAILED.code, DEPLOY_FAILED.name());
			mapAssetType.put(DELETED.code, DELETED.name());
			return mapAssetType;
		}

		public static Map<Short, String> getEnumMapAds() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(ADS_SENT.code, ADS_SENT.name());
			mapAssetType.put(ADS_FAILED.code, ADS_FAILED.name());
			mapAssetType.put(ADVERTISING.code, ADVERTISING.name());
			mapAssetType.put(ADS_COMPLETED.code, ADS_COMPLETED.name());
			return mapAssetType;
		}

		public static Map<Short, String> getEnumMapPromoClip() {
			Map<Short, String> mapAssetType = new HashMap<>();
			mapAssetType.put(REGISTERED.code, REGISTERED.name());
			mapAssetType.put(DISTRIBUTE_FAILED.code, DISTRIBUTE_FAILED.name());
			mapAssetType.put(DISTRIBUTE_COMPLETED.code, DISTRIBUTE_COMPLETED.name());
			mapAssetType.put(APPROVED.code, APPROVED.name());
			mapAssetType.put(REJECTED.code, REJECTED.name());
			return mapAssetType;
		}

		public static Short[] getValueArray() {

			return new Short[]{REGISTERED.code, APPROVED.code, REJECTED.code, PREPROCESSING.code,
					PREPROCESS_COMPLETED.code, PREPROCESS_FAILED.code, DISTRIBUTING.code, DISTRIBUTE_COMPLETED.code,
					DISTRIBUTE_FAILED.code, READY_FOR_DEPLOYING.code, NON_READY_FOR_DEPLOYING.code,
					DEPLOY_COMPLETED.code, DEPLOY_FAILED.code, ADS_SENT.code, ADS_FAILED.code, ADS_COMPLETED.code};
		}
	}

	public enum AdsRating {
		ALL_AGE(0, "01"), UNDER_3_YEARS(3, "02"), UNDER_6_YEARS(6, "03"), UNDER_13_YEARS(13, "04"), UNDER_18_YEARS(18, "05"), ADULT(20, "06");
		private int cmsAge;
		private String adsAge;

		AdsRating(int cmsAge, String adsAge) {
			this.cmsAge = cmsAge;
			this.adsAge = adsAge;
		}

		public int getCmsAge() {
			return cmsAge;
		}

		public String getAdsAge() {
			return adsAge;
		}

		public static AdsRating getAdsRatingByCmsAge(int cmsAge) {
			switch (cmsAge) {
				case 0:
					return ALL_AGE;
				case 3:
					return UNDER_3_YEARS;
				case 6:
					return UNDER_6_YEARS;
				case 13:
					return UNDER_13_YEARS;
				case 18:
					return UNDER_18_YEARS;
				case 20:
					return ADULT;
				default:
					return ALL_AGE;
			}
		}
	}

	public enum TranscodeType {
		NONE((short) 0, "none"), STB_ONLY((short) 1, "stb"), OTT_ONLY((short) 2, "mobile"),
		STB_OTT((short) 3, "stb&mobile"), UNKNOWN((short) -1, "Unknown");
		private short value;
		private String name;

		TranscodeType(short value, String name) {
			this.value = value;
			this.name = name;
		}

		public short getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}

		public static short getValueByName(String name) {
			switch (name) {
				case "stb":
					return STB_ONLY.getValue();
				case "mobile":
					return OTT_ONLY.getValue();
				case "stb&mobile":
					return STB_OTT.getValue();
				case "none":
					return NONE.getValue();
				default:
					return UNKNOWN.getValue();
			}
		}

		public static String getNameByValue(short value) {
			switch (value) {
				case (short) 0:
					return NONE.name;
				case (short) 1:
					return STB_ONLY.name;
				case (short) 2:
					return OTT_ONLY.name;
				case (short) 3:
					return STB_OTT.name;
				default:
					return UNKNOWN.name;
			}
		}
	}

	public enum VideoAssetLocalizedType {
		SUBTITLE((byte) 1), DUB_EXTERNAL((byte) 2), DUB_EMBEDDED((byte) 3), UNKNOWN((byte) -1);
		private byte value;

		VideoAssetLocalizedType(byte value) {
			this.value = value;
		}

		public byte getValue() {
			return this.value;
		}

		public static VideoAssetLocalizedType getVideoAssetLocalizedTypeFromValue(byte val) {
			switch (val) {
				case 1:
					return SUBTITLE;
				case 2:
					return DUB_EXTERNAL;
				case 3:
					return DUB_EMBEDDED;
				default:
					return UNKNOWN;
			}
		}
	}

	public enum UploadParserState {
		FOUND((short) 1), UPLOAD_COMPLETED((short) 2), PARSING((short) 3), EXTRACTING((short) 5),
		READY_TO_USE((short) 4), REGISTERED((short) 0), ERROR((short) -1), DELETED((short) -2), UNKNOWN((short) -99);

		private short value;

		UploadParserState(short value) {
			this.value = value;
		}

		public short getValue() {
			return this.value;
		}

		public static Map<String, Short> getEnumMap() {
			Map<String, Short> enumMap = new HashMap<>();

			enumMap.put(FOUND.name(), FOUND.value);
			enumMap.put(PARSING.name(), PARSING.value);
			enumMap.put(EXTRACTING.name(), EXTRACTING.value);
			enumMap.put(READY_TO_USE.name(), READY_TO_USE.value);
			enumMap.put(REGISTERED.name(), REGISTERED.value);
			enumMap.put(ERROR.name(), ERROR.value);
			enumMap.put(DELETED.name(), DELETED.value);
			enumMap.put(UNKNOWN.name(), UNKNOWN.value);
			enumMap.put(UPLOAD_COMPLETED.name(), UPLOAD_COMPLETED.value);
			return enumMap;
		}
	}

	public enum LoginMessage {
		INVALID_CAPTCHA("login.captcha.error"), INVALID_USER_OR_PASSWD("login.user.invalid"),
		CP_AUTHORIZATION("login.user.cp.author"), OP_AUTHORIZATION("login.user.op.author"),
		UNKNOWN_ERROR("error.unknown");

		private String message;

		LoginMessage(String msg) {
			this.message = msg;
		}

		public String getMessage() {
			return this.message;
		}

	}

	public enum ExtractPosterStatus {
		EXTRACTING_FAILED((short) -1), EXTRACTING_COMPLETED((short) 0), CREATED((short) 1), EXTRACTING((short) 3);

		ExtractPosterStatus(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<String, Short> getEnumMap() {
			Map<String, Short> enumMap = new HashMap<>();

			enumMap.put(EXTRACTING_FAILED.name(), EXTRACTING_FAILED.code);
			enumMap.put(EXTRACTING_COMPLETED.name(), EXTRACTING_COMPLETED.code);
			enumMap.put(CREATED.name(), CREATED.code);
			enumMap.put(EXTRACTING.name(), EXTRACTING.code);
			return enumMap;
		}
	}

	public enum AssetExtractPosterType {
		FAILED((short) -1), COMPLETED((short) 0), CREATED((short) 1), REQUEST_SENT((short) 2),
		REQUEST_FAILED((short) -2), EXTRACTING((short) 3), ERROR((short) 99);

		AssetExtractPosterType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static AssetExtractPosterType getExtractTypeByCode(short code) {
			switch (code) {
				case 0:
					return COMPLETED;
				case -1:
					return FAILED;
				case 1:
					return CREATED;
				case -2:
					return REQUEST_FAILED;
				case 2:
					return REQUEST_SENT;
				case 3:
					return EXTRACTING;
				case 99:
					return ERROR;
				default:
					return ERROR;
			}
		}
	}

	public enum AssetTranscodeStatusType {
		COMPLETED_TRANSCODE((short) 0), FAILED((short) -1), CREATED((short) 1), TRANSFER_INPUT_SUCCESS((short) 2),
		TRANSFER_INPUT_FAILED((short) -2), REQUEST_SENT((short) 3), REQUEST_FAILED((short) -3),
		TRANS_PROCESSING((short) 4), TRANSFER_OUTPUT_SUCCESS((short) 5), TRANSFER_OUTPUT_FAILED((short) -5),
		ERROR((short) 99);

		AssetTranscodeStatusType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(FAILED.code, FAILED.name());
			map.put(COMPLETED_TRANSCODE.code, COMPLETED_TRANSCODE.name());
			map.put(CREATED.code, CREATED.name());
			map.put(REQUEST_SENT.code, REQUEST_SENT.name());
			map.put(REQUEST_FAILED.code, REQUEST_FAILED.name());
			map.put(TRANSFER_INPUT_SUCCESS.code, TRANSFER_INPUT_SUCCESS.name());
			map.put(TRANSFER_OUTPUT_SUCCESS.code, TRANSFER_OUTPUT_SUCCESS.name());
			map.put(TRANSFER_INPUT_FAILED.code, TRANSFER_INPUT_FAILED.name());
			map.put(TRANSFER_OUTPUT_FAILED.code, TRANSFER_OUTPUT_FAILED.name());
			map.put(TRANS_PROCESSING.code, TRANS_PROCESSING.name());
			return map;
		}

		public static AssetTranscodeStatusType getAssetTranscodeStatusByCode(Short code) {
			switch (code) {
				case -1:
					return FAILED;
				case 0:
					return COMPLETED_TRANSCODE;
				case 1:
					return CREATED;
				case 2:
					return TRANSFER_INPUT_SUCCESS;
				case -2:
					return TRANSFER_INPUT_FAILED;
				case 3:
					return REQUEST_SENT;
				case -3:
					return REQUEST_FAILED;
				case 4:
					return TRANS_PROCESSING;
				case 5:
					return TRANSFER_OUTPUT_SUCCESS;
				case -5:
					return TRANSFER_OUTPUT_FAILED;
				default:
					return ERROR;
			}
		}
	}

	public enum AssetTranscodeEncryptType {
		Y(Boolean.TRUE, "Y"), N(Boolean.FALSE, "N");

		AssetTranscodeEncryptType(boolean code, String name) {
			this.code = code;
			this.name = name;
		}

		private boolean code;
		private String name;

		public boolean getCode() {
			return code;
		}

		public String getName() {
			return name;
		}

		public static String getNameByCode(Boolean truefalse) {
			if (Boolean.TRUE.equals(truefalse)) {
				return Y.getName();
			}
			return N.getName();
		}

		public static Boolean getCodeByName(String encryptTypeName) {
			if ("Y".equals(encryptTypeName)) {
				return Boolean.TRUE;
			}
			return Boolean.FALSE;
		}
	}

	public enum responsePosterResizeStatus {
		DONE(0), WAITING(1), ERROR(-1), WORKING(2);
		private Integer code;

		public Integer getCode() {
			return code;
		}

		responsePosterResizeStatus(Integer code) {
			this.code = code;
		}
	}

	public enum ImageMetaDataStatus {
		FOUND((short) 1), PARSING((short) 2), READY_TO_USE((short) 3), ASSET_CREATED((short) 0),
		ERROR_PARSING((short) -1), DELETED((short) -2);
		private short value;

		ImageMetaDataStatus(short value) {
			this.value = value;
		}

		public short getValue() {
			return this.value;
		}
	}

	public enum AssetDistributeStatusType {
		FAILED((short) -1), COMPLETED((short) 0), CREATED((short) 1), REQUEST_SENT((short) 3),
		REQUEST_FAILED((short) -3), TRANSFER_SUCCESS((short) 2), TRANSFER_FAILED((short) -2), UPLOADING((short) 4),
		ERROR((short) 99);

		AssetDistributeStatusType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(FAILED.code, FAILED.name());
			map.put(COMPLETED.code, COMPLETED.name());
			map.put(CREATED.code, CREATED.name());
			map.put(REQUEST_SENT.code, REQUEST_SENT.name());
			map.put(REQUEST_FAILED.code, REQUEST_FAILED.name());
			map.put(TRANSFER_SUCCESS.code, TRANSFER_SUCCESS.name());
			map.put(TRANSFER_FAILED.code, TRANSFER_FAILED.name());
			map.put(UPLOADING.code, UPLOADING.name());
			return map;
		}

		public static AssetDistributeStatusType getAssetDistributeStatusByCode(Short code) {
			switch (code) {
				case -1:
					return FAILED;
				case 0:
					return COMPLETED;
				case 1:
					return CREATED;
				case 3:
					return REQUEST_SENT;
				case -3:
					return REQUEST_FAILED;
				case 2:
					return TRANSFER_SUCCESS;
				case -2:
					return TRANSFER_FAILED;
				case 4:
					return UPLOADING;
				default:
					return ERROR;
			}
		}
	}

	public enum OfferPackageType {
		SINGLE((short) 0), PACKAGE((short) 1), SERIES((short) 2);

		OfferPackageType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(SINGLE.code, SINGLE.name());
			map.put(SERIES.code, SERIES.name());
			map.put(PACKAGE.code, PACKAGE.name());
			return map;
		}

		public static OfferPackageType getOfferPackageTypeByCode(Short code) {
			switch (code) {
				case 0:
					return SINGLE;
				case 1:
					return PACKAGE;
				case 2:
					return SERIES;
				default:
					return SINGLE;
			}
		}

		public static String getPackageType(short code) {
			switch (code) {
				case 0:
					return "single";
				case 1:
					return "package";
				case 2:
					return "series";
				default:
					return null;
			}
		}
	}

	public enum OfferProductType {
		SINGLE((short) 0), PACKAGE((short) 1), SERIES((short) 2), SUBSCRIPTION((short) 3);

		OfferProductType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(SINGLE.code, SINGLE.name());
			map.put(PACKAGE.code, PACKAGE.name());
			map.put(SERIES.code, SERIES.name());
			map.put(SUBSCRIPTION.code, SUBSCRIPTION.name());
			return map;
		}

		public static OfferProductType getOfferProductTypeByCode(Short code) {
			switch (code) {
				case 0:
					return SINGLE;
				case 1:
					return PACKAGE;
				case 2:
					return SERIES;
				case 3:
					return SUBSCRIPTION;
				default:
					return SINGLE;
			}
		}

		public static String getProductType(short code) {
			switch (code) {
				case 0:
					return "single";
				case 1:
					return "package";
				case 2:
					return "series";
				case 3:
					return "subscription";
				default:
					return null;
			}
		}

		public static String getProductTypeForExcelExport(short code) {
			switch (code) {
				case 0:
					return "Single";
				case 1:
					return "Package";
				case 2:
					return "Series";
				case 3:
					return "Subscription";
				default:
					return "N/A";
			}
		}
	}

	public enum ProductPaymentType {
		MONTHLY((short) 1), ONCE((short) 2);

		ProductPaymentType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(MONTHLY.code, MONTHLY.name());
			map.put(ONCE.code, ONCE.name());
			return map;
		}

		public static ProductPaymentType getProductPaymentTypeByCode(Short code) {
			switch (code) {
				case 1:
					return MONTHLY;
				case 2:
					return ONCE;
				default:
					return ONCE;
			}
		}

		public static String getProductPricingType(short code) {
			switch (code) {
				case 1:
					return "Monthly";
				case 2:
					return "Once";
				default:
					return null;
			}
		}
	}

	public enum ProductPricingType {
		MONTHLY((short) 1), RENT((short) 2), BUY((short) 3);

		ProductPricingType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(MONTHLY.code, MONTHLY.name());
			map.put(RENT.code, RENT.name());
			map.put(BUY.code, BUY.name());
			return map;
		}

		public static ProductPricingType getProductPaymentTypeByCode(Short code) {
			switch (code) {
				case 1:
					return MONTHLY;
				case 2:
					return RENT;
				case 3:
					return BUY;
				default:
					return RENT;
			}
		}

		public static String getProductPricingType(short code) {
			switch (code) {
				case 1:
					return "Monthly";
				case 2:
					return "Rent";
				case 3:
					return "Buy";
				default:
					return null;
			}
		}
	}

	public enum ProductServiceType {
		PACKAGE((short) 1), SERIES((short) 2);

		ProductServiceType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(PACKAGE.code, PACKAGE.name());
			map.put(SERIES.code, SERIES.name());
			return map;
		}

		public static ProductServiceType getProductServiceTypeByCode(Short code) {
			switch (code) {
				case 1:
					return PACKAGE;
				case 2:
					return SERIES;
				default:
					return PACKAGE;
			}
		}

		public static String getProductServiceType(short code) {
			switch (code) {
				case 1:
					return "package";
				case 2:
					return "series";
				default:
					return null;
			}
		}
	}

	public enum InAppPurchaseType {
		SINGLE((short) 1), SUBSCRIPTION((short) 2);

		InAppPurchaseType(short code) {
			this.code = code;
		}

		private short code;

		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(SINGLE.code, SINGLE.name());
			map.put(SUBSCRIPTION.code, SUBSCRIPTION.name());
			return map;
		}

		public static InAppPurchaseType getProductPaymentTypeByCode(Short code) {
			switch (code) {
				case 1:
					return SINGLE;
				case 2:
					return SUBSCRIPTION;
				default:
					return SINGLE;
			}
		}

		public static String getInAppPurchaseType(short code) {
			switch (code) {
				case 1:
					return "single";
				case 2:
					return "subscription";
				default:
					return null;
			}
		}
	}

	public enum OfferRentalType {
		RENT((short) 0), BUY((short) 1), SUBSCRIPTION((short) 2);
		private short code;

		OfferRentalType(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(RENT.code, RENT.name());
			map.put(BUY.code, BUY.name());
			map.put(SUBSCRIPTION.code, SUBSCRIPTION.name());
			return map;
		}

		public static OfferRentalType getOfferPackageTypeByCode(Short code) {
			switch (code) {
				case 0:
					return RENT;
				case 1:
					return BUY;
				case 2:
					return SUBSCRIPTION;
				default:
					return RENT;
			}
		}

		public static String getRenTalName(short code) {
			switch (code) {
				case 0:
					return "rent";
				case 1:
					return "buy";
				case 2:
					return "subscription";
				default:
					return null;
			}
		}

		public static String getRenTalNameForExcel(short code) {
			switch (code) {
				case 0:
					return "Rent";
				case 1:
					return "Buy";
				case 2:
					return "Subscription";
				default:
					return "N/A";
			}
		}
	}

	public enum VideoStreamType {
		AUDIO("audio"), VIDEO("video"), UNKNOWN("unknown");
		private String codeName;

		VideoStreamType(String codeName) {
			this.codeName = codeName;
		}

		public String getCodeName() {
			return this.codeName;
		}

		public static VideoStreamType getVideoStreamType(String codeName) {
			switch (codeName) {
				case "audio":
					return AUDIO;
				case "video":
					return VIDEO;
				default:
					return UNKNOWN;
			}
		}
	}

	public enum BadgeType {
		HOT((short) 1), EVENT((short) 2), UHD((short) 3), PREMIUM((short) 4);
		private short code;

		BadgeType(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(HOT.code, HOT.name());
			map.put(EVENT.code, EVENT.name());
			map.put(UHD.code, UHD.name());
			map.put(PREMIUM.code, PREMIUM.name());
			return map;
		}

		public static BadgeType getBadgeTypeByCode(Short code) {
			switch (code) {
				case 1:
					return HOT;
				case 2:
					return EVENT;
				case 3:
					return UHD;
				case 4:
					return PREMIUM;
				default:
					return HOT;
			}
		}

		public static String getBadgeTypeName(short code) {
			switch (code) {
				case 1:
					return "hot";
				case 2:
					return "event";
				case 3:
					return "uhd";
				case 4:
					return "premium";
				default:
					return "hot";
			}
		}
	}

	public enum ContentProviderType {
		CP_TYPE((short) 1), SO((short) 2), AD_TYPE((short) 3);
		private short type;

		ContentProviderType(short type) {
			this.type = type;
		}


		public short getType() {
			return type;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(CP_TYPE.type, "Content Provider");
			map.put(SO.type, SO.name());
			map.put(AD_TYPE.type, "AD Content Provider");
			return map;
		}
		public static Map<Short, String> getCPAndADMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(CP_TYPE.type, "Content Provider");
			map.put(AD_TYPE.type, "AD Content Provider");
			return map;
		}

		public static ContentProviderType getContentProviderType(Short type) {
			switch (type) {
				case 1:
					return CP_TYPE;
				case 2:
					return SO;
				case 3:
					return AD_TYPE;
				default:
					return SO;
			}
		}
	}

	public enum VideoMetaDataType {
		NORMAL((short) 0), PROMO((short) 1);
		private short code;

		VideoMetaDataType(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(NORMAL.code, NORMAL.name());
			map.put(PROMO.code, PROMO.name());
			return map;
		}

		public static VideoMetaDataType getVideoMetaDataTypeByCode(Short code) {
			switch (code) {
				case 0:
					return NORMAL;
				case 1:
					return PROMO;
				default:
					return NORMAL;
			}
		}

	}

	public enum AssetDeployStatusType {
		ERROR((short) -1), NON_READY((short) 0), CREATED((short) 1), NON_VALID_TO_DEPLOY((short) 2),
		COMPLETED((short) 3), NONE((short) 99);
		private short code;

		AssetDeployStatusType(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(ERROR.code, ERROR.name());
			map.put(NON_READY.code, NON_READY.name());
			map.put(CREATED.code, CREATED.name());
			map.put(NON_VALID_TO_DEPLOY.code, NON_VALID_TO_DEPLOY.name());
			map.put(COMPLETED.code, COMPLETED.name());
			return map;
		}

		public static AssetDeployStatusType getAssetDeployStatusByCode(Short code) {
			switch (code) {
				case -1:
					return ERROR;
				case 0:
					return NON_READY;
				case 1:
					return CREATED;
				case 2:
					return NON_VALID_TO_DEPLOY;
				case 3:
					return COMPLETED;
				default:
					return NONE;
			}
		}
	}

	public enum ResponseStatus {
		SUCCESS((int) 200), CREATED((int) 201), BAD_REQUEST((int) 400), INTERNAL_SERVER_ERROR((int) 500);
		private int statusCode;

		ResponseStatus(int statusCode) {
			this.statusCode = statusCode;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public static String getStatusNameByCode(short statusCode) {
			switch (statusCode) {
				case 200:
					return SUCCESS.name();
				case 201:
					return CREATED.name();
				case 400:
					return BAD_REQUEST.name();
				case 500:
					return INTERNAL_SERVER_ERROR.name();
				default:
					return SUCCESS.name();
			}
		}
	}

	public enum AssetADSStatusType {
		FAILED((short) -1), COMPLETED((short) 0), CREATED((short) 1), AD_REQUEST_SENT((short) 2),
		AD_REQUEST_ERROR((short) -2), AD_PROCESSING((short) 3), ERROR((short) 99);
		private short code;

		AssetADSStatusType(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(FAILED.code, FAILED.name());
			map.put(COMPLETED.code, COMPLETED.name());
			map.put(CREATED.code, CREATED.name());
			map.put(AD_REQUEST_SENT.code, AD_REQUEST_SENT.name());
			map.put(AD_REQUEST_ERROR.code, AD_REQUEST_ERROR.name());
			map.put(AD_PROCESSING.code, AD_PROCESSING.name());
			map.put(ERROR.code, ERROR.name());
			return map;
		}

		public static AssetADSStatusType getADSStatusByCode(short code) {
			switch (code) {
				case -1:
					return FAILED;
				case 0:
					return COMPLETED;
				case 1:
					return CREATED;
				case 2:
					return AD_REQUEST_SENT;
				case -2:
					return AD_REQUEST_ERROR;
				case 3:
					return AD_PROCESSING;
				default:
					return ERROR;
			}
		}
	}

	public enum ProductHistoryStatus {
		SUCCESS((short) 0), FAILURE((short) 1);
		private short code;

		ProductHistoryStatus(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(SUCCESS.code, SUCCESS.name());
			map.put(FAILURE.code, FAILURE.name());
			return map;
		}
	}

	public enum ProductHistoryMethod {
		ADD((short) 0), UPDATE((short) 1), DELETE((short) 2);
		private short code;

		ProductHistoryMethod(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(ADD.code, ADD.name());
			map.put(UPDATE.code, UPDATE.name());
			map.put(DELETE.code, DELETE.name());
			return map;
		}
	}

	public enum PreviewContentType {
		TRAILER((short) 1), BASIC((short) 2);
		private short code;

		PreviewContentType(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(TRAILER.code, TRAILER.name());
			map.put(BASIC.code, BASIC.name());
			return map;
		}

		public static String getNameByCode(short code) {
			if (code == 1) {
				return TRAILER.name().toLowerCase();
			} else {
				return BASIC.name().toLowerCase();
			}
		}
	}

	public enum ParsingPurchaseReportStatus {
		NON_PARSING((short) 0), PARSING((short) 1), SUCCESS((short) 2), FAILED((short) -1);
		private short status;

		ParsingPurchaseReportStatus(short status) {
			this.status = status;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(NON_PARSING.status, NON_PARSING.name());
			map.put(PARSING.status, PARSING.name());
			map.put(SUCCESS.status, SUCCESS.name());
			map.put(FAILED.status, FAILED.name());
			return map;
		}


		public short getStatus() {
			return status;
		}
	}

	public enum ReportType {
		AUTO((short) 0), MANUAL((short) 1);
		private short type;

		ReportType(short type) {
			this.type = type;
		}


		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(AUTO.type, AUTO.name());
			map.put(MANUAL.type, MANUAL.name());
			return map;
		}

		public short getType() {
			return type;
		}
	}

	public enum Report_Period_Type {
		DAILY((short) 0), MONTHLY((short) 1);
		private short type;

		Report_Period_Type(short type) {
			this.type = type;
		}


		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(DAILY.type, DAILY.name());
			map.put(MONTHLY.type, MONTHLY.name());
			return map;
		}

		public short getType() {
			return type;
		}
	}

	public static class CURRENCY {
		public static final String THB = "THB";
		public static final String USD = "USD";

		private CURRENCY() {
		}

	}

	public static class ProductImportPackageID {
		public static final String B1001 = "B1001";
		public static final String B1002 = "B1002";
		public static final String B1003 = "B1003";
		public static final String B1004 = "B1004";
		public static final String B1005 = "B1005";

		private ProductImportPackageID() {
		}
	}

	public static class AD_RESULT_CODE {
		// VOD result code
		public static final int VOD_SUCCESS_CODE = 100;
		public static final int VOD_FAILURE_CODE = 200;
		public static final int VOD_SKIP_CODE = 300;

		public static final String SKIP_MESSAGE = "ADVERTISING";
		public static final String SUCCESS_MESSAGE = "SUCCESS";
		public static final String FAILURE_MESSAGE = "FAILURE";

		// AD RESULT CODE
		public static final int AD_SUCCESS_CODE = 200;
		public static final int AD_BAD_REQUEST_CODE = 400;
		public static final int AD_NOT_FOUND_CODE = 404;
		public static final int AD_CONFLICT_CODE = 409;
		public static final int AD_INTERNAL_DB_ERROR_CODE = 500;
		public static final int AD_INTERNAL_SERVER_ERROR_CODE = 1000;

		// MENU RESULT CODE
		public static final int MENU_SUCCESS_CODE = 100;
		public static final int MENU_FAILURE_CODE = 200;
		public static final int MENU_SKIP_CODE = 300;
	}

	public static class CP_REPORT_CODE {
		//MAKE REPORT STATUS
		public static final int SUCCESS_CODE = 200;
		public static final String SUCCESS_MESSAGE = "success";
		public static final int WARNING_CODE = 201;
		public static final int FAILURE_CODE = 500;
		public static final String FAILURE_MESSAGE = "failure";
	}

	public enum PromoHistoryStatus {
		SUCCESS((short) 0), FAILURE((short) 1);
		private short code;

		PromoHistoryStatus(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(SUCCESS.code, SUCCESS.name());
			map.put(FAILURE.code, FAILURE.name());
			return map;
		}
	}

	public enum PromoStatus {
		SUCCESS((short) 0), FAILURE((short) 1);
		private short code;

		PromoStatus(short code) {
			this.code = code;
		}


		public short getCode() {
			return code;
		}

		public static Map<Short, String> getEnumMap() {
			Map<Short, String> map = new HashMap<>();
			map.put(SUCCESS.code, SUCCESS.name());
			map.put(FAILURE.code, FAILURE.name());
			return map;
		}

		public static String getMessage(short code) {
			switch (code) {
				case 0:
					return "succeed";
				case 1:
					return "failed";
				default:
					return "succeed";
			}
		}
	}

	public enum AssetErrorFlag {
		NONE((short) 0), CREATED_NEW((short) 1), PREPROCESSING_FAILED((short) 2), DISTRIBUTING_FAILED((short) 3), DEPLOY_FAILED((short) 4);
		private short flag;

		AssetErrorFlag(short flag) {
			this.flag = flag;
		}


		public short getFlag() {
			return flag;
		}

		public static AssetErrorFlag getAssetErrorFlagByFlag(Short flag) {
			switch (flag) {
				case 0:
					return NONE;
				case 1:
					return CREATED_NEW;
				case 2:
					return PREPROCESSING_FAILED;
				case 3:
					return DISTRIBUTING_FAILED;
				case 4:
					return DEPLOY_FAILED;
				default:
					return NONE;
			}
		}
	}

	public enum DeployFlag {
		MENU_MAP_VERSION((short) 1);
		private short flag;

		DeployFlag(short flag) {
			this.flag = flag;
		}


		public short getFlag() {
			return flag;
		}

		public static DeployFlag getDeployFlag(short flag) {
			switch (flag) {
				case 1:
					return MENU_MAP_VERSION;
				default:
					return MENU_MAP_VERSION;
			}
		}
	}

	public enum ApiErrorCode {
		UNKNOWN_ERROR("CMS_0001"), BAD_REQUEST("CMS_0002"), UNAUTHORIZED("CMS_0003"),
		INVALID_INPUT("CMS_0004"), DB_UNKNOWN_ERROR("CMS_0011"), DB_CONNECTION_FAILURE("CMS_0012"),
		DB_QUERY_FAILURE("CMS_0013"), NAS_ERROR("CMS_0021"), REQUIRED_FIELD_MISSING("CMS_0101"),
		INVALID_DATA_FORMAT("CMS_0102"), MPP_CONNECTION_VALUE("CMS_0111"), DIS_CONN_FAILURE("CMS_0121"),
		DIS_FAILURE_RESPONSE("CMS_0122"), MBS_CONN_FAILURE("CMS_0131"), DBS_CONN_FAILURE("CMS_0141"),
		PRO_CONN_FAILURE("CMS_0151"), ADS_CONN_FAILURE("CMS_0161"), CREATING_CP_REPORT_FAILURE("CMS_0201");

		private String errorCode;

		ApiErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public String getErrorCode() {
			return errorCode;
		}

		public String getErrorMessage(ApiErrorCode errorCode) {
			switch (errorCode) {
				case UNKNOWN_ERROR:
					return "Unknown Error";
				case BAD_REQUEST:
					return "잘못된 요청 (Bad Request)";
				case UNAUTHORIZED:
					return "인증되지 않음 (Unauthorized)";
				case INVALID_INPUT:
					return "유효하지 않은 입력 (Invaid Input)";
				case DB_UNKNOWN_ERROR:
					return "DB Unknown Error";
				case DB_CONNECTION_FAILURE:
					return "DB Connection Failure";
				case DB_QUERY_FAILURE:
					return "DB query Faiure";
				case NAS_ERROR:
					return "NAS Error";
				case REQUIRED_FIELD_MISSING:
					return "Required Field Missing";
				case INVALID_DATA_FORMAT:
					return "Invalid Data Format";
				case MPP_CONNECTION_VALUE:
					return "MPP Connection Failure";
				case DIS_CONN_FAILURE:
					return "DistributionModule Connection Failure ";
				case DIS_FAILURE_RESPONSE:
					return "Distribution Failure Response";
				case MBS_CONN_FAILURE:
					return "MBS Connection Failure";
				case DBS_CONN_FAILURE:
					return "DBS Connection Failure";
				case PRO_CONN_FAILURE:
					return "PRO Connection Failure";
				case ADS_CONN_FAILURE:
					return "ADS Connection Failure";
				case CREATING_CP_REPORT_FAILURE:
					return "Failed to Create CP Sales Report";
				default:
					return "Unknown Error";

			}
		}
	}
	
	@Getter
	@AllArgsConstructor
	public enum PointType {
		ISSUE("issue"),  									// 발급
		PUBLISH("publish"),  								// 발행
		POINT_PRODUCT("point_product"),  					// 충전
		VOD_MILEAGE("vod_mileage"),  						// 적립 (VOD 구매)
		PRODUCT_MILEAGE("product_mileage"), 				// 적립 (포인트 상품 구매)
		EXPIRED_POINT("expired_point"), 					// 유효 기간 만료
		PAYMENT_VOD("payment_vod"), 						// 사용
		CANCEL_POINT_PRODUCT("cancel_point_product"),		// 충전 취소
		CANCEL_VOD_MILEAGE("cancel_vod_mileage"),			// 적립 취소  (VOD 구매)
		CANCEL_PRODUCT_MILEAGE("cancel_product_mileage"),	// 적립 취소 (포인트 상품 구매)
		CANCEL_PAYMENT_VOD("cancel_payment_vod"),			// 사용 취소
		UNREGISTERED_MEMBER("unregistered_member");			// 회원 탈퇴
		
	    private String point_type;
	}

	@Getter
	@AllArgsConstructor
	public enum SalesType {
		RATE("rate"),
		PRICE("price");
		
	    private String sales_type;
	}
	
	@Getter
	@AllArgsConstructor
	public enum ExpireType {
		DATE("d"),
		MONTH("m"),
		YEAR("y"),
		CONSTANT("c");
		
	    private String expire_type;
	}
	 
	@Getter
	@AllArgsConstructor
	public enum MileagePolicyType {
		NORMAL("normal"),
		PROMOTION("promotion");
		
	    private String policy_type;
	}
	
	@Getter
	@AllArgsConstructor
	public enum MileagePromotionType {
		CONTENT("content"),
		CATEGORY("category"),
		MONTHLY("monthly");
		
	    private String promotion_type;
	}
}