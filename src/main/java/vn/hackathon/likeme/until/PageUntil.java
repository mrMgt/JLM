package vn.hackathon.likeme.until;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static vn.hackathon.likeme.constant.SystemConstant.LIMIT_DEFAULT;


/**
 * Created by bangnl on 3/11/16.
 */
public class PageUntil {

    public  static Pageable getPageDefault(){
        return getPageDefault(null);
    }

    public  static Pageable getPageDefault(String columnSort){
        Sort sort = new Sort(Sort.Direction.ASC,columnSort);
        if(StringUtils.isBlank(columnSort)){
            return new PageRequest(0,LIMIT_DEFAULT);
        }
        return new PageRequest(0,LIMIT_DEFAULT,sort);
    }
}
