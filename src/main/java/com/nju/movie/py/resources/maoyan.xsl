<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <movies>
            <xsl:for-each select="maoyanTop100MovieInfo/movie">
                <movie>
                    <title>
                        <xsl:value-of select="title"/>
                    </title>
                    <rank>
                        <xsl:value-of select="index"/>
                    </rank>
                    <director>
                    </director>
                    <actors>
                        <xsl:value-of select="actors"/>
                    </actors>
                    <date>
                        <xsl:value-of select="time"/>
                    </date>
                    <rate>
                        <xsl:value-of select="score"/>
                    </rate>
                    <country>
                    </country>
                    <geners>
                    </geners>
                    <comCount>
                    </comCount>
                    <img>
                        <xsl:value-of select="image"/>
                    </img>
                    <content>
                    </content>
                    <playUrls></playUrls>
                </movie>
            </xsl:for-each>
        </movies>
    </xsl:template>

</xsl:stylesheet>