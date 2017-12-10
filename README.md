# SpectraSearchDatabase
交联质谱匹配
说明：
-包名
    --类名
        ---函数
-peptide：处理读取肽段文件并根据交联肽段的八种情况生成离子
    --Peptide:肽段类
    --LinkedPeptide:交联肽段类
    --ReadPeptide:读取肽段文件的类
    --HandlePeptide:根据读取的肽段，生成所有可能的交联肽段及相应的所有可能离子
    --PeptideIndex：交联肽段根据parent masss监理快速索引
-spectra：处理谱文件
    --Peak：谱峰类文件
    --Spectra：谱图类
    --ReadSpectra：读取谱图文件的类
-search：谱图搜索交联肽的数据库
    --MachEntry：结果类，保存一个谱及对应的搜索到的交联肽
    --SearchMethod：谱图搜索数据库的类，里面可以自定义打分方法
-utils：常用类文件集合
    --FilePath：肽段文件，谱图文件的路径
    --BaseMass：常量文件类，如氨基酸，水的质量
    --IonMass：一个离子类
