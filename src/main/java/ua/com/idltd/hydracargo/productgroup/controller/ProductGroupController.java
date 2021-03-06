package ua.com.idltd.hydracargo.productgroup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.com.idltd.hydracargo.productgroup.repository.Fin_ProductGroupRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Collections;
import java.util.List;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;


@RestController
@RequestMapping("/productgroup")
public class ProductGroupController {

    private final Fin_ProductGroupRepository fin_productGroupRepository;

    public ProductGroupController(Fin_ProductGroupRepository fin_productGroupRepository) {
        this.fin_productGroupRepository = fin_productGroupRepository;
    }

    @RequestMapping(value = {"","/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index(
    ){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/productgroup/cover");
        return mav;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "fpg_id", required = false) Long fpg_id) {
        JSONDatatable result = new JSONDatatable();
        if (fpg_id != null) {
            result.setData(Collections.singleton(fin_productGroupRepository.findById(fpg_id)));
        }else {
            result.setData(fin_productGroupRepository.findAll());
        }
        return result;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/add_productgroup")
    public ResponseEntity<?> add_productgroup(
            @RequestParam(name = "fpg_name") String fpg_name,
            @RequestParam(name = "fpg_price") Double fpg_price,
            @RequestParam(name = "fpg_price_brand") Double fpg_price_brand
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddQuery = entityManager
                    .createStoredProcedureQuery("PKG_PRODUCTGROUP.pr_AddProductGroup")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.OUT)
                    .setParameter(1, fpg_name)
                    .setParameter(2, fpg_price)
                    .setParameter(3, fpg_price_brand)
                    ;
            AddQuery.execute();
            result = ResponseEntity.ok(AddQuery.getOutputParameterValue(4));
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/edit_productgroup")
    public ResponseEntity<?> edit_productgroup(
            @RequestParam(name = "fpg_id", required = false) Long fpg_id,
            @RequestParam(name = "fpg_name", required = false) String fpg_name,
            @RequestParam(name = "fpg_price") Double fpg_price,
            @RequestParam(name = "fpg_price_brand") Double fpg_price_brand
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery EditProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_PRODUCTGROUP.pr_EditProductGroup")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Double.class, ParameterMode.IN)
                    .setParameter(1, fpg_id)
                    .setParameter(2, fpg_name)
                    .setParameter(3, fpg_price)
                    .setParameter(4, fpg_price_brand)
                    ;
            EditProductQuery.execute();
            result = ResponseEntity.ok(fpg_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/del_productgroup")
    public ResponseEntity<?> del_productgroup(
            @RequestParam(name = "fpg_id", required = false) Long fpg_id
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery DelProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_PRODUCTGROUP.pr_DelProductGroup")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, fpg_id)
                    ;
            DelProductQuery.execute();
            result = ResponseEntity.ok(fpg_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
