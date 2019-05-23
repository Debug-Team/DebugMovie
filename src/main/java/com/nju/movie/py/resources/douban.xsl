<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <movies>
            <xsl:for-each select="doubanTop100MovieInfo/movie">
                <movie>
                    <title>
                        <xsl:value-of select="title"/>
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
                        <xsl:value-of select="date"/>
                    </date>
                    <rate>
                        <xsl:value-of select="rate"/>
                    </rate>
                    <country>
                        <xsl:value-of select="country"/>
                    </country>
                    <geners>
                        <xsl:value-of select="geners"/>
                    </geners>
                    <comCount>
                        <xsl:value-of select="comCount"/>
                    </comCount>
                    <img>
                        <xsl:value-of select="image"/>
                    </img>
                    <content>
                    </content>
                    <playUrls>
                        <xsl:for-each select="playUrls/playUrl">
                            <playUrl>
                                <name>
                                    <xsl:value-of select="playName"/>
                                </name>
                                <url>
                                    <xsl:value-of select="playUrl"/>
                                </url>
                                <price>
                                    <xsl:value-of select="playPrice"/>
                                </price>
                            </playUrl>
                        </xsl:for-each>
                    </playUrls>
                </movie>
            </xsl:for-each>
        </movies>
    </xsl:template>

</xsl:stylesheet>