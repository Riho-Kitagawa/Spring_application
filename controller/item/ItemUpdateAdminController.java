package jp.co.sss.shop.controller.item;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import jp.co.sss.shop.bean.ItemBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.form.ItemForm;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.BeanCopy;
import jp.co.sss.shop.util.Constant;

/**
 * 商品管理 変更機能のコントローラクラス
 *
 * @author SystemShared
 */
@Controller
public class ItemUpdateAdminController {

	/**
	 * 商品情報
	 */
	@Autowired
	ItemRepository itemRepository;

	/**
	 * カテゴリ情報
	 */
	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * 商品情報変更入力処理
	 *
	 * @param id    商品情報
	 * @param model Viewとの値受渡し
	 * @return "item/update/item_update_input" カテゴリ情報 変更入力画面へ
	 */
	@RequestMapping(path = "/item/update/input", method = RequestMethod.POST)
	public String updateInput(boolean backFlg, Integer id, Model model, @ModelAttribute ItemForm form) {

		// 戻るボタンで遷移してきたか判定
		if (!backFlg) {
			// 変更対象の商品情報取得
			Item item = itemRepository.findById(id).orElse(null);

			// 商品情報の生成
			ItemBean itemBean = BeanCopy.copyEntityToBean(item);

			// 商品情報をViewに渡す
			model.addAttribute("item", itemBean);

		} else {
			ItemBean itemBean = BeanCopy.copyFormToBean(form);

			// 会員情報をViewに渡す
			model.addAttribute("item", itemBean);
		}
		return "item/update/item_update_input";
	}

	
	/**
	 * エラー時入力画面に遷移する
	 * @return "item/update/item_update_input" 商品情報変更入力画面
	 */
	@RequestMapping(path = "/item/update/input", method = RequestMethod.GET)
	public String updateInputError() {
		return "item/update/item_update_input";
	}
	
	/**
	 * 商品情報変更確認処理
	 *
	 * @param form 商品情報
	 * @return "item/update/item_update_check" 商品情報 変更確認画面へ
	 */
	@RequestMapping(path = "/item/update/check", method = RequestMethod.POST)
	public String updateCheck(@Valid @ModelAttribute ItemForm form, BindingResult result, Model model,RedirectAttributes redirectAttributes) {

		// 入力値にエラーがあった場合、入力画面に戻る
		if (result.hasErrors()) {
			
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.itemForm", result);

			redirectAttributes.addFlashAttribute("item", form);

			return "redirect:/item/update/input";

		}

		if (form.getImageFile().getSize() > 0) {
			// 画像ファイルがある場合、ファイルを生成
			MultipartFile file = form.getImageFile();

			// アップロード対象のファイル名を取得
			String imageName = file.getOriginalFilename();

			// 現在の日時を「yyyyMMddhhmmss」形式の文字列として取得
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String date = dateFormat.format(new Date());

			// ファイルのアップロード先を指定
			imageName = date + "_" + imageName;
			File uploadPath = new File(Constant.FILE_UPLOAD_PATH, imageName);

			try {
				// 指定されたファイルを一時的にアップロード
				file.transferTo(uploadPath);
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}

			// 一時的にアップロードしたファイルの名前をFormクラスにセット
			form.setImage(imageName);
		} else {
			// 商品画像が未入力の場合、登録済みの画像ファイルを取得する
			Item item = itemRepository.findById(Integer.parseInt(form.getId())).orElse(null);
			form.setImage(item.getImage());
		}

		// 選択したカテゴリの名前をFormクラスにセット
		Category category = categoryRepository.findById(Integer.parseInt(form.getCategoryId())).orElse(null);
		form.setCategoryName(category.getName());

		return "item/update/item_update_check";
	}

	/**
	 * 商品情報変更完了処理
	 *
	 * @param form 商品情報
	 * @param redirectAttributes リダイレクト後情報保持
	 * @return "redirect:/item/update/complete" 商品情報 登録完了画面へ
	 */
	@RequestMapping(path = "/item/update/complete", method = RequestMethod.POST)
	public String updateComplete(@ModelAttribute ItemForm form,RedirectAttributes redirectAttributes) {
	
		// Formクラス内の各フィールドの値をエンティティにコピー
		Item item = BeanCopy.copyFormToEntity(form);
		
		// 商品情報の削除フラグを初期化
		item.setDeleteFlag(Constant.NOT_DELETED);

		//FlashAttributeに商品情報を格納
		redirectAttributes.addFlashAttribute("itemId",item.getId());
		
		// 商品情報を保存
		itemRepository.save(item);

		return "redirect:/item/update/complete";
	}
	
	/**
	 * 商品情報変更完了表示
	 *
	 * @return "item/update/item_update_complete" 商品情報 登録完了画面へ
	 */
	@RequestMapping(path = "/item/update/complete", method = RequestMethod.GET)
	public String updateCompleteRedirect() {
		
		return "item/update/item_update_complete";
	}	

}
