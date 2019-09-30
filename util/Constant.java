package jp.co.sss.shop.util;

/**
 * 定数定義用クラス
 *
 * @author SystemShared
 */
public class Constant {
	/** 削除フラグの値(未削除状態) */
	public static final int NOT_DELETED = 0;

	/** 削除フラグの値(削除状態) */
	public static final int DELETED = 1;

	/** インデックス番号の初期値 */
	public static final int DEFAULT_INDEX = 1;

	/** 表示順の初期値(新着順) */
	public static final int DEFAULT_SORT_TYPE = 1;

	/** 戻るフラグの値（戻るボタン押下時） */
	public static final int BACK_FLG_TRUE = 1;

	/**
	 * 商品画像のアップロード先 (注意) ファイルの保存場所はeclipseの環境構築の操作内容によってことなる場合があります。
	 * ファイルアップロードに失敗した場合は、下記のパスを修正してください。
	 */
	public static final String FILE_UPLOAD_PATH = "C:/Users/edu/Documents/workspace-spring-tool-suite-4-4.1.1.RELEASE/shared_shop_ForTraining/src/main/resources/static/img";
}
