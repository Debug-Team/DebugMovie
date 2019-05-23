<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <movies>
            <xsl:for-each select="timeTop100MovieInfo/movie">
                <movie>
                    <title>
                        <xsl:value-of select="name"/>
                    </title>
                    <rank>
                        <xsl:value-of select="index"/>
                    </rank>
                    <director>
                        <xsl:value-of select="director"/>
                    </director>
                    <actors>
                        <xsl:value-of select="actors"/>
                    </actors>
                    <date>
                    </date>
                    <rate>
                        <xsl:value-of select="point"/>
                    </rate>
                    <country>
                    </country>
                    <geners>
                    </geners>
                    <comCount>
                        <xsl:value-of select="numbers"/>
                    </comCount>
                    <img>
                        <xsl:value-of select="image"/>
                    </img>
                    <content>
                        <xsl:value-of select="realcontent"/>
                    </content>
                    <playUrls></playUrls>
                </movie>
            </xsl:for-each>
        </movies>
    </xsl:template>

</xsl:stylesheet>