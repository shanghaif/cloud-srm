package com.midea.cloud.srm.model.base.material.dto;

import com.midea.cloud.srm.model.base.material.MaterialItem;
import com.midea.cloud.srm.model.base.material.entity.ItemImage;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *  功能名称 物料维护
 * </pre>
 *
 * @author haiping2.li@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/9/28 10:32
 *  修改内容:
 * </pre>
 */
@Data
@Accessors(chain = true)
public class MaterialItemDto implements Serializable {
	private MaterialItem materialItem;
	private List<Fileupload> matFiles;
	private List<MaterialItemErrorDto> errorDtos;
	private List<ItemImage> itemImages;
	private int number;
}
