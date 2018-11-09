echo "清理旧文件 ..."
rm -rf *.tar.gz *.md5 extension

module_version="module.version"
property_file="src/main/resources/application.properties"
has_version=`grep "^$module_version=" $property_file | wc -l`
if [ "$has_version" -ne "0" ]; then
  sed -i '/^module\.version=/d' $property_file
fi
version=`awk '/<version>[^<]+<\/version>/{gsub(/<version>|<\/version>/,"",$1);print $1;exit;}' pom.xml`

has_eureka_enable_setting=`grep "^eureka.client.enabled=" $property_file | wc -l`
if [ "$has_eureka_enable_setting" -ne "0" ]; then
  sed -i '/^eureka.client.enabled=/d' $property_file
fi
echo "eureka.client.enabled=true" >> $property_file

echo "$module_version=$version" >> $property_file
image_url="registry.fit2cloud.com/fit2cloud2-extention/module-demo:latest"
image_name=`echo $image_url | awk -F"/" '{ print $3 }'`
image=`echo $image_name | awk -F":" '{ print $1 }'`

echo "编译工程源码 ..."
mvn clean package -U -Dmaven.test.skip=true

echo "构建扩展模块镜像 ..."
docker build -t $image_url .
#docker push registry.fit2cloud.com/fit2cloud2-extention/security-scan:master

mkdir extension
echo "导出扩展模块镜像 ..."
docker save -o extension/${image_name}.tar ${image_url}
sed -i '/^version=/d' service.inf
echo "version=$build_version" >> service.inf
\cp service.inf extension/
\cp service.ico extension/
\cp docker-compose.yml extension/

echo "制作扩展模块安装包"
install_file=$image-$version.tar.gz
tar zcvf $install_file extension

md5_file_name=${install_file}.md5
md5sum ${install_file} | awk -F" " '{print "md5: "$1}' > ${md5_file_name}

git checkout $property_file service.inf
