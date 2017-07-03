%define __jar_repack 0
Name: service-lapps-converter
Version: 1.0
Release: 1
Summary: service-lapps-converter
License: (c) null
Group: Applications/System
autoprov: yes
autoreq: yes
BuildArch: noarch
BuildRoot: /Users/felahi/repository/converter/service-lapps-converter/target/rpm/service-lapps-converter/buildroot

%description

%install
if [ -d $RPM_BUILD_ROOT ];
then
  mv /Users/felahi/repository/converter/service-lapps-converter/target/rpm/service-lapps-converter/tmp-buildroot/* $RPM_BUILD_ROOT
else
  mv /Users/felahi/repository/converter/service-lapps-converter/target/rpm/service-lapps-converter/tmp-buildroot $RPM_BUILD_ROOT
fi

%files

 "/usr/share/service-lapps-converter/"
%dir %attr(700,sneconverter,sneconverter) "/var/lib/service-lapps-converter/"
%dir %attr(700,sneconverter,sneconverter) "/var/log/service-lapps-converter/"
%attr(755,-,-)  "/usr/bin/service-lapps-converter"
%attr(755,-,-)  "/etc/init.d/service-lapps-converter"
%config   "/etc/default/service-lapps-converter"
%config   "/etc/clarind/service-lapps-converter.yaml"

%pre
/usr/bin/getent group sneconverter > /dev/null || /usr/sbin/groupadd sneconverter
                            /usr/bin/getent passwd sneconverter > /dev/null || /usr/sbin/useradd -r -d /var/lib/service-lapps-converter -m -g sneconverter sneconverter

%post
chkconfig --add service-lapps-converter;
                            if [ $1 -eq 0 ]; then
                            /sbin/service service-lapps-converter start
                            elif [ $1 -ge 1 ]; then
                            /sbin/service service-lapps-converter restart
                            fi

%preun
if [ $1 -eq 0 ] ; then
                            /sbin/service service-lapps-converter stop;chkconfig --del service-lapps-converter
                            fi
