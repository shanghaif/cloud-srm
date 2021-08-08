
package com.midea.cloud.srm.model.suppliercooperate.deliverynote.soap.erpacceptvendorshipmentsoapbiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "APPS.CUX_RCV_ASN_IFACX1362720X1X7", propOrder = {
    "pasninfotblitem"
})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class APPSCUXRCVASNIFACX1362720X1X7 {

    @XmlElement(name = "P_ASN_INFO_TBL_ITEM", nillable = true)
    protected List<APPSCUXRCVASNIFACX1362720X1X8> pasninfotblitem;


}
