package ua.com.idltd.hydracargo.request.boxcontent.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.com.idltd.hydracargo.request.boxcontent.repository.BoxcontentRepository;
import ua.com.idltd.hydracargo.utils.JSONDatatable;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.Date;

import static ua.com.idltd.hydracargo.utils.StaticUtils.ConvertTraceExceptionToText;


@RestController
@RequestMapping("/boxcontent")
public class BoxContentController {

    @PersistenceContext
    private EntityManager entityManager;

    private final BoxcontentRepository boxcontentRepository;

    public BoxContentController(BoxcontentRepository boxcontentRepository) {
        this.boxcontentRepository = boxcontentRepository;
    }

    @PostMapping("/gettable")
    public JSONDatatable gettable(@RequestParam(name = "box_id", required = false) Long box_id) {
        JSONDatatable result = new JSONDatatable();
        if (box_id!=null) result.setData(boxcontentRepository.findByBOX_ID(box_id));
        return result;
    }

    @PostMapping("/add_boxcontent")
    public ResponseEntity<?> add_boxcontent(
            @RequestParam(name = "box_id") Long box_id,
            @RequestParam(name = "bc_num") String bc_num,
            @RequestParam(name = "bc_description") String bc_description,
            @RequestParam(name = "bc_mark") Long bc_mark,
            @RequestParam(name = "bc_count") Long bc_count,
            @RequestParam(name = "bc_weight") Double bc_weight,
            @RequestParam(name = "bc_unitprice") Double bc_unitprice
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_Box.addBoxContent")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(8, Long.class, ParameterMode.OUT)
                    .setParameter(1, box_id)
                    .setParameter(2, bc_num)
                    .setParameter(3, bc_description)
                    .setParameter(4, bc_mark)
                    .setParameter(5, bc_count)
                    .setParameter(6, bc_weight)
                    .setParameter(7, bc_unitprice)
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(AddProductQuery.getOutputParameterValue(8));
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/edit_boxcontent")
    public ResponseEntity<?> edit_boxcontent(
            @RequestParam(name = "bc_id") Long bc_id,
            @RequestParam(name = "box_id") Long box_id,
            @RequestParam(name = "bc_num") String bc_num,
            @RequestParam(name = "bc_description") String bc_description,
            @RequestParam(name = "bc_mark") Long bc_mark,
            @RequestParam(name = "bc_count") Long bc_count,
            @RequestParam(name = "bc_weight") Double bc_weight,
            @RequestParam(name = "bc_unitprice") Double bc_unitprice
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_Box.updateBoxContent")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Double.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(8, Double.class, ParameterMode.IN)
                    .setParameter(1, bc_id)
                    .setParameter(2, box_id)
                    .setParameter(3, bc_num)
                    .setParameter(4, bc_description)
                    .setParameter(5, bc_mark)
                    .setParameter(6, bc_count)
                    .setParameter(7, bc_weight)
                    .setParameter(8, bc_unitprice)
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(bc_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    @PostMapping("/del_boxcontent")
    public ResponseEntity<?> delete_boxcontent(
            @RequestParam(name = "bc_id") Long bc_id
    ) {
        ResponseEntity<?> result;
        try{
            StoredProcedureQuery AddProductQuery = entityManager
                    .createStoredProcedureQuery("PKG_Box.deleteBoxContent")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .setParameter(1, bc_id)
                    ;
            AddProductQuery.execute();
            result = ResponseEntity.ok(bc_id);
        }
        catch (Exception e) {
            result = new ResponseEntity<>(ConvertTraceExceptionToText(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
