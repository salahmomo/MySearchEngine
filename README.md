My Search engine

build crawler, indexer and retroindex how work indeprndently.

MainCrawler: Lauch a crawler who crawler web page and send information to the Indexer
MainIndex: Server who receive information from crawlerand process it. the process information is save in file for the retro-index in IndexFiles folder.
MainRetroIndex: Server who read all file in IndexFiles folder and index it. use -retroIndexupdate to update retroIndex
MainRetroIndexSpark: Save retroIndex but we can get information from server via Rest Call.