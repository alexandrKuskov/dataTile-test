package com.qatestlab.base;

import org.openqa.selenium.By;

public class Locator {
    private LocatorTypes type;
    private String value;

    public Locator(LocatorTypes type, String locatorPattern)
    {
        this.type = type;
        value = locatorPattern;
    }

    public By getLocator(Object... args) {
        String locator = String.format(value, args);
        switch (type) {
            case XPATH:
                return By.xpath(locator);
            case CSS:
                return By.cssSelector(locator);
            case ID:
                return By.id(locator);
            case NAME:
                return By.name(locator);
            case TAG:
                return By.tagName(locator);
            case LINK_TEXT:
                return By.linkText(locator);
            case PARTIAL_LINK_TEXT:
                return By.partialLinkText(locator);
            default:
                return By.xpath(locator);
        }
    }

    /**
     * Concatenate a child locator with parent locator.
     * @param childLocator
     * @return
     */
    public Locator concat(Locator childLocator)
    {
        String[] variants = toXpath().split("[|]");
        String finalXpath = "";
        for (String v: variants) {
            if (finalXpath.length() > 0)
                finalXpath += " | ";
            finalXpath +=v.trim() + childLocator.toXpath();
        }

        return new Locator(LocatorTypes.XPATH, finalXpath);
    }

    /**
     * Convert value to XPath representation
     * @return XPath value
     */
    private String toXpath()
    {
        switch (type)
        {
            case XPATH:
                return value;
            case CSS:
                // by class name
                if (value.startsWith(".")) {
                    return String.format("//*[contains(@class,'%s')]", value.substring(1));
                } else {
                    return value; // TODO! for other values
                }
            case ID:
                return String.format("//*[@id='%s']", value);
            case NAME:
                return String.format("//*[@name='%s']", value);
            case TAG:
                return String.format("//%s", value);
            case LINK_TEXT:
                return String.format("//a[text()='%s']", value);
            case PARTIAL_LINK_TEXT:
                return String.format("//a[contains(text(),'%s')]", value);
            default: return value;
        }
    }
}
