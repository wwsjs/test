import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '工号/学号',
    align:"center",
    dataIndex: 'workNo'
   },
   {
    title: '姓名',
    align:"center",
    dataIndex: 'username'
   },
   {
    title: '联系电话',
    align:"center",
    dataIndex: 'phone'
   },
   {
    title: '邮箱',
    align:"center",
    dataIndex: 'email'
   },
   {
    title: '办公室/实验室',
    align:"center",
    dataIndex: 'roomNo'
   },
   {
    title: '人员类别',
    align:"center",
    dataIndex: 'personType_dictText'
   },
   {
    title: '年级',
    align:"center",
    dataIndex: 'grade'
   },
   {
    title: '课题组编号',
    align:"center",
    dataIndex: 'groupId_dictText'
   },
   {
    title: '管理员/组长/用户',
    align:"center",
    dataIndex: 'roleCode_dictText'
   },
];