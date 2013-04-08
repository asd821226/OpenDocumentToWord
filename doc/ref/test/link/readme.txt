>>>
word 連結加法

直接反白欲增加的連結文字, 並點選mouse右鍵後, 選擇"超連結",

選取已存在的標題即可.


>>>可能的改法

希望增加bookmark之處, 可參考下列xml

<!-- table name:功能表:start -->
<w:p w:rsidP="007373F2" w:rsidR="007373F2" w:rsidRDefault="007373F2">
    <w:pPr>
        <w:pStyle w:val="2"/>
        <w:rPr>
            <w:rFonts w:ascii="Arial" w:cs="Arial" w:hAnsi="Arial"/>
            <w:color w:val="7F007F"/>
            <w:sz w:val="28"/>
            <w:szCs w:val="28"/>
        </w:rPr>
    </w:pPr>
    
    <!-- key point:start -->
    <w:bookmarkStart w:id="0" w:name="_DLC.FUNCTION_DATA_功能表"/>
    <w:bookmarkEnd w:id="0"/>
    <!-- key point:end -->
    
    <w:r>
        <w:rPr>
            <w:rFonts w:ascii="Arial" w:cs="Arial" w:hAnsi="Arial"/>
            <w:color w:val="7F007F"/>
            <w:sz w:val="28"/>
            <w:szCs w:val="28"/>
        </w:rPr>
        <w:lastRenderedPageBreak/>
        <w:t>DLC.FUNCTION_DATA </w:t>
    </w:r>
    <w:r>
        <w:rPr>
            <w:rFonts w:ascii="Arial" w:cs="Arial" w:hAnsi="Arial"/>
            <w:color w:val="7F007F"/>
            <w:sz w:val="28"/>
            <w:szCs w:val="28"/>
        </w:rPr>
        <w:t>功能表</w:t>
    </w:r>
</w:p>
<!-- table name:功能表:end -->

>>>
文字超連結的可能改法...

可參考相關 link.xml 中的程式

<!-- 超連結tag:可參考:2 -->
<w:hyperlink w:anchor="_DLC.FUNCTION_DATA_功能表"
    w:history="1">
    <w:r w:rsidR="007373F2" w:rsidRPr="00635E9F">
        <w:rPr>
            <w:rStyle w:val="a3" />
            <w:rFonts w:ascii="Arial" w:cs="Arial" w:eastAsia="新細明體"
                w:hAnsi="Arial" />
            <w:kern w:val="0" />
            <w:sz w:val="20" />
        </w:rPr>
        <w:t>功能表</w:t>
    </w:r>
</w:hyperlink>

:::並加以比對link.xml及link_clear.xml


