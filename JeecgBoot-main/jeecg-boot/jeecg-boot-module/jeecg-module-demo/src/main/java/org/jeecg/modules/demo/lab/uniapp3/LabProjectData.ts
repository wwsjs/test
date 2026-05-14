import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '项目编号',
    align:"center",
    dataIndex: 'projectNo'
   },
   {
    title: '项目名称',
    align:"center",
    dataIndex: 'projectName'
   },
   {
    title: '开始时间',
    align:"center",
    dataIndex: 'startDate',
   },
   {
    title: '结束时间',
    align:"center",
    dataIndex: 'endDate',
   },
   {
    title: '项目负责人ID',
    align:"center",
    dataIndex: 'leaderId_dictText'
   },
   {
    title: '项目成员ID',
    align:"center",
    dataIndex: 'memberId_dictText'
   },
   {
    title: '合同经费',
    align:"center",
    dataIndex: 'contractAmount'
   },
   {
    title: '所属组',
    align:"center",
    dataIndex: 'groupId_dictText'
   },
];