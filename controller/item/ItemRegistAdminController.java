package jp.co.sss.shop.controller.item;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.form.ItemForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * 商品管理 登録機能のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class ItemRegistAdminController {

	/**
	 * サーブレットコンテキスト
	 */
	@Autowired
	ServletContext			context;

	/**
	 * カテゴリ情報
	 */
	@Autowired
	CategoryRepository		categoryRepository;

	/**
	 * 商品情報
	 */
	@Autowired
	public ItemRepository	itemRepository;

	/**
	 * 商品情報登録画面の表示処理
	 *
	 * @param Model Viewとの値受渡し
	 * @return "item/regist/item_regist_input" 商品情報 登録画面へ
	 */
	@RequestMapping(path = "/item/regist/input", method = RequestMethod.GET)
	public String registInput(Model model) {
		
		if (!model.containsAttribute("itemForm")) {
			model.addAttribute("itemForm", new ItemForm());
		}
		
		return "item/regist/item_regist_input";
	}
	
	/**
	 * 商品情報登録入力画面へ戻る
	 *
	 * @return "item/regist/item_regist_input" 商品情報 登録画面へ
	 */
	@RequestMapping(path = "/item/regist/input", method = RequestMethod.POST)
	public String registInputBack(ItemForm form) {
		
		return "item/regist/item_regist_input";
	}
	

	/**
	 * 商品情報登録確認処理
	 *
	 * @param form
	 *            商品情報
	 * @param result
	 *            入力値チェックの結果
	 * @return 
	 * 入力値エラーあり："redirect:/item/regist/input" 商品情報登録画面へ 
	 * 入力値エラーなし："item/regist/item_regist_check" 商品情報登録確認画面へ
	 *         
	 */
	@RequestMapping(path = "/item/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute ItemForm form, BindingResult result
			,RedirectAttributes redirectAttributes ) {

		if (result.hasErrors()) {
			// 入力値にエラーがあった場合、入力画面に戻る
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemForm", result);

			redirectAttributes.addFlashAttribute("itemForm", form);

			return "redirect:/item/regist/input";
		}

		if (form.getImageFile().getSize() > 0) {
			// 画像ファイルが選択されている場合
			MultipartFile file = form.getImageFile();

			// アップロード対象のファイル名を取得
			String imageName = file.getOriginalFilename();

			// アップロードされたファイル名が絶対パスで取得された場合に名前を修正
			if (imageName.lastIndexOf("\\") != -1) {
				imageName = imageName.substring(imageName.lastIndexOf("\\") + 1);
			}

			// 現在の日時を「yyyyMMddhhmmss」形式の文字列として取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String date = dateFormat.format(new Date());

			// ファイルのアップロード先を指定
			imageName = date + "_" + imageName;
			File uploadPath = new File(Constant.FILE_UPLOAD_PATH, imageName);
			try {
			Thread.sleep(500);
			
				// 指定されたファイルを一時的にアップロード
				file.transferTo(uploadPath);
			}
			catch (Exception e) {
				e.printStackTrace();
				return "error";
			}

			// 一時的にアップロードしたファイルの名前をFormクラスにセット
			form.setImage(imageName);
		}

		// 選択したカテゴリの名前をFormクラスにセット
		Category category = categoryRepository.findById(Integer.parseInt(form.getCategoryId())).orElse(null);
		form.setCategoryName(category.getName());

		return "item/regist/item_regist_check";
	}

	/**
	 * 商品情報登録完了処理
	 *
	 * @param form 商品情報
	 * @return "redirect:/item/regist/complete" 商品情報 登録完了画面へ
	 */
	@RequestMapping(path = "/item/regist/complete", method = RequestMethod.POST)
	public String registComplete(@ModelAttribute ItemForm form) {
		// Formクラス内の各フィールドの値をエンティティにコピー
		Item item = BeanCopy.copyFormToEntity(form);

		// 商品情報を保存
		itemRepository.save(item);

		return "redirect:/item/regist/complete";
	}
	
	/**
	 * 商品情報登録完了画面
	 *
	 * @return "item/regist/item_regist_complete" 商品情報 登録完了画面へ
	 */
	@RequestMapping(path = "/item/regist/complete", method = RequestMethod.GET)
	public String registCompleteRedirect() {
		
		return "item/regist/item_regist_complete";
	}

	
}
