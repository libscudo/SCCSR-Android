package ar.com.ezequielaceto.mobile.android.libraries.pki;

public class SCCSRAttributes {
    private String commonName, organization, organizationUnit, country, stateOrProvince, locality, serialNumber;

    public SCCSRAttributes(String commonName, String organization, String organizationUnit, String country, String stateOrProvince, String locality, String serialNumber) {
        this.commonName = commonName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.country = country;
        this.stateOrProvince = stateOrProvince;
        this.locality = locality;
        this.serialNumber = serialNumber;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getOrganization() {
        return organization;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public String getCountry() {
        return country;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public String getLocality() {
        return locality;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
